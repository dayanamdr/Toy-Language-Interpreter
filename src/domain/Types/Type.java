package domain.Types;
import domain.Values.Value;

public interface Type {
    Value defaultValue();

    Type clone();
}
