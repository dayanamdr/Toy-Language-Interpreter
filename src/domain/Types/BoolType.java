package domain.Types;

import domain.Values.BoolValue;

public class BoolType implements Type {
    public boolean equals(Object second) {
        if (second instanceof BoolType) {
            return true;
        }
        return false;
    }
    public String toString() {
        return "bool ";
    }
    public BoolValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type clone() {
        return new BoolType();
    }
}
