package model.expressions;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class ReadHeap implements Expression{
    private final Expression expression;

    public ReadHeap(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws InterpreterException {
        Value addressComponent = expression.eval(table, heap);

        if (!(addressComponent.getType() instanceof ReferenceType)) throw new InterpreterException("ERROR: The expressions does not result in an reference type.");
        ReferenceValue referenceValue = (ReferenceValue) addressComponent;
        return heap.get(referenceValue.getAddress());
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        }
        else throw new InterpreterException("ERROR: The readHeap argument is not a reference type.");
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeap(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("ReadHeap{%s}", expression.toString());
    }
}
