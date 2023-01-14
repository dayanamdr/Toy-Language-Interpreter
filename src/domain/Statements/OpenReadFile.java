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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFile implements IStatement {
    private final Expression exp;

    public OpenReadFile(Expression expression) {
        this.exp = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, ADTException {
        Value value = exp.evaluate(state.getSymbolTable(), state.getHeap());
        if (value.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) value;
            IDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fileName.getValue())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getValue()));
                } catch (FileNotFoundException e) {
                    throw new MyException(fileName.toString() + " couldn't be open.");
                }
                fileTable.put(fileName.getValue(), br);
                state.setFileTable(fileTable);
            } else {
                throw new MyException(fileName.toString() + "is already open.");
            }
        } else {
            throw new MyException(exp.toString() + " doesn't evaluate to StringType.");
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (exp.typecheck(typeEnv).equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("OpenReadFile requires a string expression.");
        }
    }

    @Override
    public IStatement clone() {
        return new OpenReadFile(exp.clone());
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", exp.toString());
    }

}
