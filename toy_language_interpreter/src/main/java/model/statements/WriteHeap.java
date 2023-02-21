package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.ReferenceType;
import model.values.ReferenceValue;
import model.values.Value;
import model.types.Type;

import java.io.IOException;

public class WriteHeap implements IStatement{
    private final String variableName;
    private final Expression expression;

    public WriteHeap(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    public String getVariableName() {
        return variableName;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, IOException {
        if (!state.getSymbolTable().contains_key(variableName)) throw new InterpreterException("ERROR: The variable is not declared.");
        Value variableValue = state.getSymbolTable().get(variableName);

        if (!(variableValue.getType() instanceof ReferenceType)) throw new InterpreterException("ERROR: The stored variable is not of Reference type.");
        ReferenceValue referenceValue = (ReferenceValue) variableValue;
        if (!state.getHeap().contains_key(referenceValue.getAddress())) throw new InterpreterException(("ERROR: The address from the reference value is not in the heap."));


        Value evaluatedExpression = expression.eval(state.getSymbolTable(), state.getHeap());
        Type locationType = referenceValue.getLocation_type();

        if (!locationType.equals(evaluatedExpression.getType())) throw new InterpreterException("ERROR: The location type of the variable and of the expression are not matching.");

        state.getHeap().put(referenceValue.getAddress(), evaluatedExpression);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        Type expressionType = expression.typeCheck(typeTable);
        if (typeTable.get(variableName).equals(new ReferenceType(expressionType))) {
            return typeTable;
        }
        throw new InterpreterException("ERROR: The variable has a different than the expression");
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeap(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("WriteHeap{%s, %s}", variableName, expression.toString());
    }
}
