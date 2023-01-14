package controller;

import domain.Exceptions.InterpreterException;
import domain.ProgramState.ProgramState;
import domain.Statements.IStatement;
import domain.Values.ReferenceValue;
import domain.Values.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepository repo;
    boolean displayFlag = false;

    ExecutorService executorService;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

//    public ProgramState oneStepExecution(ProgramState state) throws MyException, IOException, ADTException {
//        IStack<IStatement> executionStack = state.getExecutionStack();
//        if (executionStack.isEmpty()) {
//            throw new MyException("Execution stack is empty!");
//        }
//        IStatement currentStatement = (IStatement) executionStack.pop();
//        return currentStatement.execute(state);
//    }

//    public void allStepsExecution() throws MyException, IOException, ADTException {
//        ProgramState prgState = this.repo.getCurrentProgram();
//        this.printCurrentProgramState(prgState);
//        repo.logPrgStateExec();
//        while (!prgState.getExecutionStack().isEmpty()) {
//            this.oneStepExecution(prgState);
//            this.printCurrentProgramState(prgState);
//            prgState.getHeap().setContent(unsafeGarbageCollector(
//                    getAddrFromSymTable(prgState.getSymbolTable().getContent().values()), getAddrFromHeap(prgState.getHeap().getContent().values()),
//                    prgState.getHeap().getContent()
//            ));
//            repo.logPrgStateExec();
//        }
//    }

    public HashMap<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next)->next, HashMap::new));
    }

    HashMap<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddresses, HashMap<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()) || heapAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next)->next, HashMap::new));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValue) {
        return symTableValue.stream()
                .filter(v->v instanceof ReferenceValue)
                .map(v->{ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) throws InterruptedException, InterpreterException {
        programStates.forEach(programState -> {
            try {
                repo.logPrgStateExec(programState);
                //display(programState);
            } catch (IOException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::executeOneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = executorService.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // add the new threads to the list of the existing threads
        programStates.addAll(newProgramList);

        programStates.forEach(programState -> {
            try {
                repo.logPrgStateExec(programState);
                System.out.println(programState);
            } catch (IOException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        repo.setProgramStates(programStates);
    }

    public void allSteps() throws InterruptedException, InterpreterException {
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrg(repo.getProgramList());
        while (programStates.size() > 0) {
            conservativeGarbageCollector(programStates);
            oneStepForAllPrograms(programStates);
            programStates = removeCompletedPrg(repo.getProgramList());
        }
        executorService.shutdownNow();
        repo.setProgramStates(programStates);
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream().filter(p -> !p.isNotCompleted()).collect(Collectors.toList());
    }

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymbolTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }

    private void display(ProgramState programState) {
        if (displayFlag) {
            System.out.println(programState.toString());
        }
    }

    public void setProgramStates(List<ProgramState> programStates) {
        this.repo.setProgramStates(programStates);
    }

    public List<ProgramState> getProgramStates() {
        return this.repo.getProgramList();
    }

    public void oneStep() throws InterruptedException, InterpreterException {
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrg(repo.getProgramList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        // programStates = removeCompletedPrg(repo.getProgramList());
        executorService.shutdownNow();
        //repo.setProgramStates(programStates);
    }

}
