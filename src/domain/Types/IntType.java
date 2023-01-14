package domain.Types;

import domain.Values.IntValue;

public class IntType implements Type {
    public boolean equals(Object second) {
        if (second instanceof  IntType) {
            return true;
        }
        return false;
    }
    public String toString() {
        return "int ";
    }
    public IntValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type clone() {
        return new IntType();
    }
}
