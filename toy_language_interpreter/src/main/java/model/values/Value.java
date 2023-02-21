package model.values;

import model.types.Type;

public interface Value {
    boolean equals(Value anotherValue);
    Type getType();

    Value clone();

}
