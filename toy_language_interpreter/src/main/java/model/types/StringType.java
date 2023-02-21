package model.types;

import model.values.*;
import model.values.StringValue;

public class StringType implements Type{
    public boolean equals(Object obj) {
        if (obj instanceof StringType) return true;
        return false;
    }

    @Override
    public String toString() {
        return "string ";
    }
    @Override
    public Value default_value() {
        return new StringValue("");
    }

    @Override
    public StringType clone()
    {
        return new StringType();
    }
}
