package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStatement{
    private final Expression expression;

    public CloseReadFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, IOException {
        Value expressionValue = expression.eval(state.getSymbolTable(), state.getHeap());
        if (expressionValue.getType().equals(new StringType()))
        {
            StringValue toStr = (StringValue) expressionValue;
            String stringVal = toStr.getValue();

            if (state.getFileTable().contains_key(stringVal))
            {
                BufferedReader fileDescriptor = state.getFileTable().get(stringVal);
                fileDescriptor.close();

                state.getFileTable().remove(stringVal);
                return null;
            }
            else throw new InterpreterException("ERROR: File name does not exist.");
        }
        throw new InterpreterException("ERROR: The expression does not result in a string.");
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        Type expressionType = expression.typeCheck(typeTable);
        if (!expressionType.equals(new StringType()))
            throw new InterpreterException("ERROR: CloseReadFile requires a string expression");

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "close(" + expression.toString() + ")";
    }
}
