package domain.Expressions;

import domain.ADTs.IDictionary;
import domain.ADTs.IHeap;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Types.Type;
import domain.Values.Value;

public interface Expression {
    Value evaluate(IDictionary<String, Value> table, IHeap heap) throws ADTException, MyException;
    Type typecheck(IDictionary<String, Type> typeEnv) throws MyException;

    @Override
    String toString();

    Expression clone();
}
