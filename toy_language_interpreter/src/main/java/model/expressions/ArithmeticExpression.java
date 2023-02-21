package model.expressions;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression{
    private Expression expression1, expression2; //
    private String operation; // 1 - '+'; 2 - '-'; 3 - '*'; 4 - '/'

    public ArithmeticExpression(String operation,Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws InterpreterException {
        Value value1 = null, value2 = null;
        value1 = expression1.eval(table, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = expression2.eval(table, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue integer1 = (IntValue) value1;
                IntValue integer2 = (IntValue) value2;
                int number1, number2;
                number1 = integer1.getValue();
                number2 = integer2.getValue();
                if (operation.equals("+")) {
                    return new IntValue(number1 + number2);
                }
                if (operation.equals("-")) {
                    return new IntValue(number1 - number2);
                }
                if (operation.equals("*")) {
                    return new IntValue(number1 * number2);
                }
                if (operation.equals("/")) {
                    if (number2 == 0) throw new InterpreterException("ERROR: Division by zero");
                    return new IntValue(number1 / number2);
                }
            } else {
                throw new InterpreterException("ERROR: second operand is not an integer");
            }
        }
        throw new InterpreterException("ERROR: first operand is not an integer");
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if (!type1.equals(new IntType())) throw new InterpreterException("ERROR: First operand is not an integer.");
        if (!type2.equals(new IntType())) throw new InterpreterException("ERROR: Second operand is not an integer.");

        return new IntType();
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(operation, expression1.deepCopy(), expression2.deepCopy());
    }

    public Expression getExpression1() {
        return expression1;
    }

    public void setExpression1(Expression expression1) {
        this.expression1 = expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public void setExpression2(Expression expression2) {
        this.expression2 = expression2;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "ArithmeticExpression{" +
                "expression1=" + expression1 +
                ", expression2=" + expression2 +
                ", operation=" + operation +
                '}';
    }
}
