package domain.Statements;

import domain.ADTs.IDictionary;
import domain.ADTs.IStack;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Expressions.Expression;
import domain.ProgramState.ProgramState;
import domain.Types.BoolType;
import domain.Types.Type;
import domain.Values.BoolValue;
import domain.Values.Value;

public class WhileStatement implements IStatement {
    private final Expression expression;
    private final IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, ADTException {
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        IStack<IStatement> stack = state.getExecutionStack();
        if (!value.getType().equals(new BoolType())) {
            throw new MyException(String.format("%s is not of BoolType", value));
        }
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getValue()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typecheck(typeEnv.clone());
            return typeEnv;
        } else {
            throw new MyException("The condition of WHILE does not have the type Boolean.");
        }
    }

    @Override
    public IStatement clone() {
        return new WhileStatement(expression.clone(), statement.clone());
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }
}
