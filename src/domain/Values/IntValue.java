package domain.Values;

import domain.Types.IntType;
import domain.Types.Type;

public class IntValue implements Value {
    private final int val;
    public IntValue(int val) {
        this.val = val;
    }
    public int getValue() {
        return this.val;
    }
    public String toString() {
        return String.valueOf(this.val);
    }
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value clone() {
        return new IntValue(val);
    }
}
