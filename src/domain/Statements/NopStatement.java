package domain.Statements;

import domain.ADTs.IDictionary;
import domain.Exceptions.MyException;
import domain.ProgramState.ProgramState;
import domain.Types.Type;

public class NopStatement implements IStatement {

    public NopStatement() {}

    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    public IStatement clone() { return new NopStatement(); }

}
