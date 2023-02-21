package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement{
    private final Expression expression;
    private final String variableName;

    public ReadFile(Expression _expression, String _variableName) {
        this.expression = _expression;
        this.variableName = _variableName;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        if (!typeTable.get(variableName).equals(new IntType()))
            throw new InterpreterException("ERROR: ReadFile requires an int as variable parameter.");
        Type expressionType = expression.typeCheck(typeTable);
        if (!expressionType.equals(new StringType()))
            throw new InterpreterException("ERROR: ReadFile requires a string expression.");

        return typeTable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, IOException {
        if (state.getSymbolTable().contains_key(variableName))
        {
            if (state.getSymbolTable().get(variableName).getType().equals(new IntType()))
            {
                Value value = expression.eval(state.getSymbolTable(), state.getHeap());
                if (value.getType().equals(new StringType()))
                {
                    StringValue toStr = (StringValue) value;
                    String stringVal = toStr.getValue();

                    if (state.getFileTable().contains_key(stringVal))
                    {
                        BufferedReader fileDescriptor = state.getFileTable().get(stringVal);
                        String line = fileDescriptor.readLine();
                        IntValue readedValue = null;

                        if (line == null) { readedValue = new IntValue(0); }
                        else { readedValue = new IntValue(Integer.parseInt(line));}

                        state.getSymbolTable().put(variableName, readedValue);
                        return null;
                    }
                    else throw new InterpreterException("ERROR: The string value is not a key in the FileTable");
                }
                else throw new InterpreterException("ERROR: The expression does not result in a string");
            }
            else throw new InterpreterException("ERROR: Associated value is not an integer.");
        }
        throw new InterpreterException("ERROR: Variable name is not a key in the symbol table.");
    }


    @Override
    public IStatement deepCopy() {
        return new ReadFile(expression.deepCopy(), variableName);
    }

    @Override
    public String toString() {
        return "read("+expression.toString() + "," + variableName + ")";
    }

    public Expression getExpression() {
        return expression;
    }

    public String getVariableName() {
        return variableName;
    }
}
