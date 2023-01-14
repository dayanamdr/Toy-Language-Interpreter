package domain.Expressions;

import domain.ADTs.IDictionary;
import domain.ADTs.IHeap;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Types.IntType;
import domain.Types.Type;
import domain.Values.IntValue;
import domain.Values.Value;

public class ArithmeticExpression implements Expression {
    private Expression exp1, exp2;
    private char op;

    public ArithmeticExpression(char op, Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public String toString() {
        return this.exp1.toString() + this.op + this.exp2.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap heap) throws ADTException, MyException {
        Value val1, val2;
        val1 = exp1.evaluate(table, heap);
        if (val1.getType().equals(new IntType())) {
            val2 = exp2.evaluate(table, heap);
            if (val2.getType().equals(new IntType())) {
                IntValue toInteger1 = (IntValue)val1;
                IntValue toInteger2 = (IntValue)val2;
                int number1, number2;
                number1 = toInteger1.getValue();
                number2 = toInteger2.getValue();
                switch (this.op) {
                    case '+':
                        return new IntValue(number1 + number2);
                    case '-':
                        return new IntValue(number1 - number2);
                    case '*':
                        return new IntValue(number1 * number2);
                    case '/':
                        if (number2 == 0)
                            throw new ADTException("Division by zero!");
                        return new IntValue(number1 / number2);
                    default:
                        throw new ADTException("Invalid operand!");
                }
            }
            else throw new ADTException("Second operand is not an int.");
        }
        else throw new ADTException("First operand is not an int.");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new MyException("Second operand is not an integer");
            }
        } else {
            throw new MyException("First operand is not an integer");
        }
    }

    @Override
    public Expression clone() {
        return new ArithmeticExpression(op, exp1.clone(), exp2.clone());
    }
}
