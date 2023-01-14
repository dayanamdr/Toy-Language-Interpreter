package domain.Values;

import domain.Types.StringType;
import domain.Types.Type;

public class StringValue implements Value {
    private final String val;
    public StringValue(String value) {
        this.val = value;
    }
    public String getValue() {
        return this.val;
    }

    @Override
    public String toString() {
        return "\"" + val + "\"";
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value clone() {
        return new StringValue(val);
    }
}
