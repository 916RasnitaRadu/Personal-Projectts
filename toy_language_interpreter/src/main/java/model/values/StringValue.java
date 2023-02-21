package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value{
    private String value;

    public StringValue(String s) {
        this.value = s;
    }

    public String getValue() { return this.value;}



    @Override
    public String toString()
    {
        return "\"" + value + "\"";
    }

    @Override
    public boolean equals(Value anotherValue) {
        if (anotherValue.getType() instanceof StringType)
        {
            StringValue castObject = (StringValue) anotherValue;
            return castObject.equals(castObject.getValue());
        }
        return false;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value clone() {
        return new StringValue(value);
    }
}
