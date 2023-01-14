package domain.Expressions;

import domain.ADTs.IDictionary;
import domain.ADTs.IHeap;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Types.ReferenceType;
import domain.Types.Type;
import domain.Values.ReferenceValue;
import domain.Values.Value;

public class ReadHeapExpression implements Expression {
    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap heap) throws ADTException, MyException {
        Value value = expression.evaluate(symbolTable, heap);
        if (!(value instanceof ReferenceValue)) {
            throw new ADTException(String.format("%s not of RefType", value));
        }
        ReferenceValue refValue = (ReferenceValue) value;
        return heap.get(refValue.getAddress());
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type instanceof ReferenceType) {
            ReferenceType refType = (ReferenceType) type;
            return refType.getInner();
        } else {
            throw new MyException("The rH argument is not a RefType.");
        }
    }

    @Override
    public Expression clone() {
        return new ReadHeapExpression(expression.clone());
    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", expression);
    }
}
