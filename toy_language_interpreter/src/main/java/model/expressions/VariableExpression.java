package model.expressions;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements Expression {
    private String key;

    public VariableExpression(String k) {
        this.key = k;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws InterpreterException {
        return table.get(key);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv.get(key);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return String.format("VariableExpression(%s)", key);
    }
}
