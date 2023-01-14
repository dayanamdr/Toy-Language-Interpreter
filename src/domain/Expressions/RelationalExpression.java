package domain.Expressions;

import domain.ADTs.IDictionary;
import domain.ADTs.IHeap;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Types.BoolType;
import domain.Types.IntType;
import domain.Types.Type;
import domain.Values.BoolValue;
import domain.Values.IntValue;
import domain.Values.Value;

public class RelationalExpression implements Expression {
    private Expression exp1, exp2;
    private String op;

    @Override
    public String toString() {
        return this.exp1.toString() + this.op + this.exp2.toString();
    }

    public RelationalExpression(String op, Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap heap) throws ADTException, MyException {
        Value value1, value2;
        value1 = exp1.evaluate(table, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = exp2.evaluate(table, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue toInteger1 = (IntValue)value1;
                IntValue toInteger2 = (IntValue)value2;
                int number1, number2;
                number1 = toInteger1.getValue();
                number2 = toInteger2.getValue();
                switch (op) {
                    case ">=":
                        return new BoolValue(number1 >= number2);
                    case ">":
                        return new BoolValue(number1 > number2);
                    case "<=":
                        return new BoolValue(number1 <= number2);
                    case "<":
                        return new BoolValue(number1 < number2);
                    case "==":
                        return new BoolValue(number1 == number2);
                    case "!=":
                        return new BoolValue(number1 != number2);
                    default:
                        throw new ADTException("Invalid operator.");
                }
            } else throw new ADTException("Second operand is not an integer.");
        } else throw new ADTException("First operand is not an integer.");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else throw new MyException("Second operand is not an integer.");
        } else throw new MyException("First operand is not an integer.");
    }

    @Override
    public Expression clone() {
        return new RelationalExpression(op, exp1.clone(), exp2.clone());
    }
}
