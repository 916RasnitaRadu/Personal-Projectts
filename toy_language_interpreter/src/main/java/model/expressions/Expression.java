package model.expressions;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.Type;
import model.values.Value;

public interface Expression {
    Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws InterpreterException;

    Type typeCheck(IDictionary<String, Type> typeEnv) throws InterpreterException;

    Expression deepCopy();
}
