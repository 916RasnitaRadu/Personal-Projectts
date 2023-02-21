package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

import java.io.IOException;

public class NewStatement implements IStatement{

    private String variableName;
    private Expression expression;

    public NewStatement(String variableName, Expression expression) {
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
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();

        if (!symbolTable.contains_key(variableName)) throw new InterpreterException("ERROR: The variable is not in the table.");
        Value variableValue = symbolTable.get(variableName);

        if (!(variableValue.getType() instanceof ReferenceType)) throw new InterpreterException("ERROR: The variable is not of ReferenceType.");
        Value evaluatedExpression = expression.eval(symbolTable, heap);

        Type locationType = ((ReferenceValue)variableValue).getLocation_type();
        if (!locationType.equals(evaluatedExpression.getType())) throw new InterpreterException("ERROR: The given location type is not matching.");

        Integer newPosition = heap.allocate(evaluatedExpression);
        symbolTable.put(variableName, new ReferenceValue(newPosition, locationType)); // update symbol table

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        Type typeVariable = typeTable.get(variableName);
        Type typeExpression = expression.typeCheck(typeTable);
        if (typeVariable.equals(new ReferenceType(typeExpression)))
            return typeTable;
        else throw new InterpreterException("ERROR: NEW Statement right hand side and left hand side have different types.");
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("New{%s, %s}", variableName, expression);
    }
}
