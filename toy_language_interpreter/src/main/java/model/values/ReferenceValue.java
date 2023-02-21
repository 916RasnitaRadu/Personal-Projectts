package model.values;

import model.types.ReferenceType;
import model.types.Type;

public class ReferenceValue implements Value{
    protected int address;
    private Type location_type;

    public ReferenceValue(int adr, Type t) {
        this.address = adr;
        this.location_type = t;
    }

    public int getAddress() { return  this.address;}

    public Type getLocation_type() { return this.location_type;}

    @Override
    public boolean equals(Value anotherValue) {
        if (anotherValue instanceof ReferenceValue)
        {
            ReferenceValue castValue = (ReferenceValue) anotherValue;
            return castValue.location_type == this.location_type && castValue.address == this.address;
        }
        return false;
    }

    @Override
    public Type getType() {
        return new ReferenceType(location_type);
    }

    @Override
    public Value clone() {
        return new ReferenceValue(this.address, this.location_type);
    }

    @Override
    public String toString() {
        return "(" + address + "," + location_type.toString() + ")";
    }
}
