package domain.Expressions;

import domain.ADTs.IDictionary;
import domain.ADTs.IHeap;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Types.Type;
import domain.Values.Value;

public class VariableExpression implements Expression {
    private String key;

    public VariableExpression(String id) {
        this.key = id;
    }

    @Override
    public String toString() {
        return this.key;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap heap) throws ADTException, MyException {
        return table.lookup(key);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(key);
    }

    @Override
    public Expression clone() {
        return new VariableExpression(key);
    }
}
