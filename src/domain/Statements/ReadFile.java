package domain.Statements;

import domain.ADTs.IDictionary;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Expressions.Expression;
import domain.ProgramState.ProgramState;
import domain.Types.IntType;
import domain.Types.StringType;
import domain.Types.Type;
import domain.Values.IntValue;
import domain.Values.StringValue;
import domain.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private final Expression exp;
    private final String varName;

    public ReadFile(Expression exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, ADTException {
        IDictionary<String, Value> symTable = state.getSymbolTable();
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isDefined(varName)) {
            Value value = symTable.lookup(varName);
            if (value.getType().equals(new IntType())) {
                value = exp.evaluate(symTable, state.getHeap());
                if (value.getType().equals(new StringType())) {
                    StringValue castValue = (StringValue) value;
                    if (fileTable.isDefined(castValue.getValue())) {
                        BufferedReader br = fileTable.lookup(castValue.getValue());
                        try {
                            String line = br.readLine();
                            if (line == null) {
                                line = "0";
                            }
                            symTable.put(varName, new IntValue(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new MyException("Could not read from file " + castValue);
                        }
                    } else {
                        throw new MyException("The file table does not contain " + castValue);
                    }
                } else {
                    throw new MyException(value.toString() + " does not evaluate to StringType.");
                }
            } else {
                throw new MyException(value.toString() + " isn't of type IntType.");
            }
        } else {
            throw new MyException(varName.toString() + " is not present in the symbolTable.");
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (exp.typecheck(typeEnv).equals(new StringType())) {
            if (typeEnv.lookup(varName).equals(new IntType())) {
                return typeEnv;
            } else {
                throw new MyException("ReadFile requires and int as its variable parameter.");
            }
        } else {
            throw new MyException("ReadFile requires a string as an expression parameter.");
        }
    }

    @Override
    public IStatement clone() {
        return new ReadFile(exp.clone(), varName);
    }

    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", exp.toString(), varName);
    }
}
