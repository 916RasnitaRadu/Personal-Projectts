package model.types;

import model.values.ReferenceValue;
import model.values.Value;

public class ReferenceType implements Type{
    protected Type inner;

    public ReferenceType(Type inner) {this.inner = inner;}

    public Type getInner() { return this.inner;}

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ReferenceType)
        {
            return inner.equals(((ReferenceType) obj).getInner());
        }
        return false;
    }

    @Override
    public Value default_value() {
        return new ReferenceValue(0, this.inner);
    }

    @Override
    public Type clone() {
        return new ReferenceType(inner.clone());
    }

    @Override
    public String toString()
    {return "Ref(" + this.inner.toString() + ")"; }
}
