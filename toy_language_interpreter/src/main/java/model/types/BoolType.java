package model.types;


import model.values.*;

public class BoolType implements Type
{

    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool ";
    }



    @Override
    public Value default_value() {
        return new BoolValue(false);
    }

    @Override
    public BoolType clone()
    {
        return new BoolType();
    }
}
