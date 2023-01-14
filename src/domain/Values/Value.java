package domain.Values;

import domain.Types.Type;

public interface Value {
    Type getType();

    Value clone();
}
