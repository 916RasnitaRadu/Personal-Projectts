package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value{
    private boolean value;

    public BoolValue(boolean value) {this.value = value;}

    public boolean getValue() { return value;}

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Value anotherValue) {
        if (anotherValue instanceof BoolValue)
        {
            BoolValue castObject = (BoolValue) anotherValue;
            return value == castObject.value;
        }
        return false;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value clone() {
        return new BoolValue(value);
    }
}
