package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.adts.IStack;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.Type;
import model.values.Value;

public class AssignStatement implements IStatement{
    private String key;
    private Expression expression;

    public AssignStatement(String key, Expression expression) {
        this.key = key;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IDictionary<String, Value> symTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();
        if (symTable.contains_key(key)) {
            Value value = expression.eval(symTable, heap);
            Type type = symTable.get(key).getType();
            if (value.getType().equals(type))
            {
                symTable.put(key, value);
            }
            else throw new InterpreterException(String.format("ERROR: %s is not compatible with %s", value.toString(), type.toString()));
        }
        else throw new InterpreterException("The used variable " + key + " was not declared before.");
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        Type typeVariable = typeTable.get(key);
        Type typeExpression = expression.typeCheck(typeTable);
        if (typeVariable.equals(typeExpression)) return typeTable;
        else throw new InterpreterException("ERROR: Assignment right hand side and left hand side have different types.");
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(key, expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return key + " = " + expression.toString();
    }

    public String getKey() {
        return key;
    }

    public Expression getExpression() {
        return expression;
    }
}
