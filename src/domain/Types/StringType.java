package domain.Types;

import domain.Values.StringValue;
import domain.Values.Value;

public class StringType implements Type {
    @Override
    public boolean equals(Object second) {
        if (second instanceof StringType) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "string ";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type clone() {
        return new StringType();
    }
}
