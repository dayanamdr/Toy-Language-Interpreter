package domain.Statements;

import domain.ADTs.IDictionary;
import domain.ADTs.MyDictionary;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.ProgramState.ProgramState;
import domain.Types.Type;

import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException, IOException, ADTException;
    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException;
    IStatement clone();
}
