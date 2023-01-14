package domain.Expressions;

import domain.ADTs.IDictionary;
import domain.ADTs.IHeap;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Types.Type;
import domain.Values.Value;

public class ValueExpression implements Expression {
    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap heap) throws ADTException, MyException {
        return value;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return value.getType();
    }

    @Override
    public Expression clone() {
        return new ValueExpression(value);
    }
}
