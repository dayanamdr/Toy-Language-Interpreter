package domain.Statements;

import domain.ADTs.IDictionary;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Expressions.Expression;
import domain.ProgramState.ProgramState;
import domain.Types.StringType;
import domain.Types.Type;
import domain.Values.StringValue;
import domain.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStatement {
    private final Expression exp;

    public CloseReadFile(Expression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, ADTException {
        Value value = exp.evaluate(state.getSymbolTable(), state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new MyException(exp.toString() + " doesn't evaluate to StringValue.");
        }
        StringValue fileName = (StringValue) value;
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if(!fileTable.isDefined(fileName.getValue())) {
            throw new MyException(value.toString() + "is not present in the FileTable.");
        }
        BufferedReader br = fileTable.lookup(fileName.getValue());
        try {
            br.close();
        } catch(IOException e) {
            throw new MyException("Unexpected error in closing " + value.toString());
        }
        fileTable.delete(fileName.getValue());
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (exp.typecheck(typeEnv).equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("CloseReadFile requires a string expression.");
        }
    }

    @Override
    public IStatement clone() {
        return new CloseReadFile(exp.clone());
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", exp.toString());
    }
}
