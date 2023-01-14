package domain.Statements;

import domain.ADTs.IDictionary;
import domain.Exceptions.MyException;
import domain.ProgramState.ProgramState;
import domain.Types.Type;
import domain.Values.Value;

public class VariableDeclarationStatement implements IStatement {
    private String name;
    private Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, Value> table = state.getSymbolTable();
        if (table.isDefined(this.name))
            throw new MyException("Variable already declared.");
        table.update(this.name, this.type.defaultValue());
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.update(name, type);
        return typeEnv;
    }

    @Override
    public IStatement clone() {
        return new VariableDeclarationStatement(name, type);
    }

    @Override
    public String toString() {
        return this.type.toString() + this.name;
    }

}
