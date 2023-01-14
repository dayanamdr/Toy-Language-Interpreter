package repository;

import domain.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();
    void setProgramStates(List<ProgramState> programStates);
    public void addProgram(ProgramState progState);

    //public ProgramState getCurrentProgram();
    void logPrgStateExec(ProgramState programState) throws IOException;
    void emptyLogFile() throws IOException;
}
