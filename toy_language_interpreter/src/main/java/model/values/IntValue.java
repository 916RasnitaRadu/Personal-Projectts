package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value{
    private int value;

    public IntValue(int v) { this.value = v;}

    public int getValue() { return this.value;}

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public boolean equals(Value anotherValue) {
        if (anotherValue.getType() instanceof IntType)
        {
            IntValue castValue = (IntValue) anotherValue;
            return value == castValue.getValue();
        }
        return false;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value clone() {
        return new IntValue(value);
    }
}
