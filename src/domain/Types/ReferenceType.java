package domain.Types;

import domain.Values.ReferenceValue;
import domain.Values.Value;

public class ReferenceType implements Type {
    private final Type inner;

    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return this.inner;
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0, this.inner);
    }

    @Override
    public Type clone() {
        return new ReferenceType(inner.clone());
    }

    public boolean equals(Object other) {
        if (other instanceof ReferenceType) {
            return this.inner.equals(((ReferenceType)other).getInner());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ref(" + this.inner.toString() + ")";
    }
}
