package model.types;

import model.values.Value;

public interface Type {
    Value default_value();

    Type clone();
}
