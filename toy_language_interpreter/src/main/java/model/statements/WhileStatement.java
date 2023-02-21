package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

import java.io.IOException;

public class WhileStatement implements IStatement {
    private final Expression expression;
    private final IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public IStatement getStatement() {
        return statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, IOException {
        Value expressionValue = expression.eval(state.getSymbolTable(), state.getHeap());
        if (!expressionValue.getType().equals(new BoolType())) throw new InterpreterException("ERROR: The expression is not of a boolean type.");
        BoolValue boolValue = (BoolValue) expressionValue;
        if (boolValue.getValue())
        {
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(statement);
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        Type type = expression.typeCheck(typeTable);
        if (type.equals(new BoolType()))
        {
            statement.typeCheck(typeTable.copy());
            return typeTable;
        }
        throw new InterpreterException("ERROR: The condition of WHILE is not of type bool.");
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("WhileStatement{%s, %s}", expression.toString(), statement.toString());
    }
}
