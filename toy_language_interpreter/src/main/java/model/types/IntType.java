package model.types;

import model.values.IntValue;

public class IntType implements Type{
    public boolean equals(Object second)
    {
        if (second instanceof IntType) return true;
        return false;
    }

    @Override
    public String toString() {
        return "int ";
    }

    @Override
    public IntValue default_value()
    {
        return new IntValue(0);
    }

    @Override
    public Type clone()
    {
        return new IntType();
    }
}
