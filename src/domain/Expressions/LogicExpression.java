package domain.Expressions;

import domain.ADTs.IDictionary;
import domain.ADTs.IHeap;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Types.BoolType;
import domain.Types.Type;
import domain.Values.BoolValue;
import domain.Values.Value;

public class LogicExpression implements Expression {
    private Expression exp1, exp2;
    private String op;

    public LogicExpression(String op, Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap heap) throws ADTException, MyException {
        Value value1, value2;
        value1 = exp1.evaluate(table, heap);
        if (value1.getType().equals(new BoolType())) {
            value2 = exp2.evaluate(table, heap);
            if (value2.getType().equals(new BoolType())) {
                BoolValue boolVal1 = (BoolValue)value1;
                BoolValue boolVal2 = (BoolValue)value2;
                boolean number1, number2;
                number1 = boolVal1.getValue();
                number2 = boolVal2.getValue();
                if (op == "and") {
                    return new BoolValue(number1 && number2);
                }
                if (op == "or") {
                    return new BoolValue(number1 || number2);
                }
                else throw new ADTException("Invalid operand.");
            } else throw new ADTException("Second operand is not a bool,");
        } throw new ADTException("First operand is not a bool.");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        if (exp1.equals(new BoolType())) {
            if (exp2.equals(new BoolType())) {
                return new BoolType();
            } else throw new MyException("Second operand is not a boolean");
        } else throw new MyException("First operand is not a boolean.");
    }

    @Override
    public String toString() {
        return this.exp1.toString() + " " + this.op + " " + this.exp2.toString();
    }

    @Override
    public Expression clone() {
        return new LogicExpression(op, exp1.clone(), exp2.clone());
    }
}
