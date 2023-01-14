package domain.Values;

import domain.Types.ReferenceType;
import domain.Types.Type;

public class ReferenceValue implements Value {
    private final int address;
    private final Type locationType;

    public ReferenceValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public Value clone() {
        return new ReferenceValue(address, locationType.clone());
    }
}
