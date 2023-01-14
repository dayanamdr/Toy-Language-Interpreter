package domain.Statements;

import domain.ADTs.IDictionary;
import domain.ADTs.IStack;
import domain.ADTs.MyDictionary;
import domain.Exceptions.MyException;
import domain.ProgramState.ProgramState;
import domain.Types.Type;

public class CompoundStatement implements IStatement {
    private IStatement first, second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public IStatement getFirst() {
        return first;
    }

    public IStatement getSecond() {
        return second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<IStatement> stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public IStatement clone() {
        return new CompoundStatement(first.clone(), second.clone());
    }

    public String toString() {
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }

}
