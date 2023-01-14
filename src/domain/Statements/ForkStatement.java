package domain.Statements;

import domain.ADTs.IDictionary;
import domain.ADTs.IStack;
import domain.ADTs.MyDictionary;
import domain.ADTs.MyStack;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.ProgramState.ProgramState;
import domain.Types.Type;
import domain.Values.Value;

import java.io.IOException;
import java.util.Map;

public class ForkStatement implements IStatement {
    private final IStatement statement;
    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException, ADTException {
        IStack<IStatement> newStack = new MyStack<>();
        newStack.push(statement);
        IDictionary<String, Value> newSymTable = new MyDictionary<>();
        for (Map.Entry<String, Value> entry : state.getSymbolTable().getContent().entrySet()) {
            newSymTable.put(entry.getKey(), entry.getValue().clone());
        }
        return new ProgramState(newStack, newSymTable, state.getOutput(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public IStatement clone() {
        return new ForkStatement(statement.clone());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", statement.toString());
    }
}
