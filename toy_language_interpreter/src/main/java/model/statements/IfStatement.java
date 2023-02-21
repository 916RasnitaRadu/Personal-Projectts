package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements IStatement{
    private Expression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, Value> symTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();
        Value value = expression.eval(symTable, heap);
        if (value.getType().equals(new BoolType()))
        {
            BoolValue condition_result = (BoolValue)value;
            if (condition_result.getValue())
            {
                state.getExecutionStack().push(thenStatement);
            }
            else {
                state.getExecutionStack().push(elseStatement);
            }
            return null;
        }
        throw new InterpreterException(String.format("ERROR: %s is not of boolean type inside the if statement", value.toString()));
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        Type typeExpression = expression.typeCheck(typeTable);
        if (!typeExpression.equals(new BoolType())) throw new InterpreterException("ERROR: The condition in IF must be of bool type.");
        thenStatement.typeCheck(typeTable.copy());
        elseStatement.typeCheck(typeTable.copy());

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("if (%s) {\n\t%s\n}else{\n\t%s\n}", expression.toString(),thenStatement.toString(),elseStatement.toString());
    }

    public Expression getExpression() {
        return expression;
    }

    public IStatement getThenStatement() {
        return thenStatement;
    }

    public IStatement getElseStatement() {
        return elseStatement;
    }
}
