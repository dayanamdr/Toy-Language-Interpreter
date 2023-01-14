package repository;

import domain.ProgramState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private int currentIndex;
    private final String logFilePath;

    public Repository(ProgramState programState, String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        this.programStates = new ArrayList<>();
        this.addProgram(programState);
        this.emptyLogFile();
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void addProgram(ProgramState progState) {
        this.programStates.add(progState);
    }

//    @Override
//    public ProgramState getCurrentProgram() {
//        return (ProgramState) this.programStates.get(this.programStates.size() -1);
//    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.toString());
        logFile.close();
    }

    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }
}