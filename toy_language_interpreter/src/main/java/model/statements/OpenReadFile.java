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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFile implements IStatement{
    private final Expression expression;

    public OpenReadFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, FileNotFoundException {
        Value expressionValue = expression.eval(state.getSymbolTable(), state.getHeap());
        if (expressionValue.getType().equals(new StringType()))
        {
            StringValue toStr = (StringValue) expressionValue;
            String key = toStr.getValue();

            if (!state.getSymbolTable().contains_key(key))
            {
                BufferedReader fileDescriptor = new BufferedReader(new FileReader(key));
                state.getFileTable().put(key, fileDescriptor);
                return null;
            }
            else throw new InterpreterException("ERROR: The filename already exists!");
        }
        throw new InterpreterException("ERROR: Expression does not result into a string.");
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        Type type = expression.typeCheck(typeTable);
        if (!type.equals(new StringType())) throw new InterpreterException("ERROR: OpenReadFile requires a string expression");

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(expression.deepCopy());
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString()
    {
        return "open(" + expression.toString() + ")";
    }
}
