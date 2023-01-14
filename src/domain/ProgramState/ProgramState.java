package domain.ProgramState;

import domain.ADTs.*;
import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;
import domain.Statements.IStatement;
import domain.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, Value> symbolTable;
    private IList<Value> output;
    private IStatement originalProgram;
    private IDictionary<String, BufferedReader> fileTable;
    private IHeap heap;
    private int id;
    private static int lastId = 0;

    public IDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setFileTable(IDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(IStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(IDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IList<Value> getOutput() {
        return output;
    }

    public void setOutput(IList<Value> output) {
        this.output = output;
    }

    public void setHeap(IHeap newHeap) {
        this.heap = newHeap;
    }

    public IHeap getHeap() {
        return this.heap;
    }

    public String heapToString() throws ADTException {
        StringBuilder heapStringBuilder = new StringBuilder();
        for (int key: heap.keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heap.get(key)));
        }
        return heapStringBuilder.toString();
    }

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, IList<Value> output,
                        IStatement originalProgram, IDictionary<String, BufferedReader> fileTable, IHeap heap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram.clone();
        this.executionStack.push(this.originalProgram);
        this.id = setId();
    }

    public ProgramState(IStack<IStatement> stack, IDictionary<String,Value> symTable, IList<Value> out, IDictionary<String, BufferedReader> fileTable, IHeap heap) {
        this.executionStack = stack;
        this.symbolTable = symTable;
        this.output = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = setId();
    }

    public ProgramState(IStatement originalProgram) {
        this.executionStack = new MyStack<IStatement>();
        this.symbolTable = new MyDictionary<String, Value>();
        this.output = new MyList<Value>();
        this.fileTable = new MyDictionary<String, BufferedReader>();
        this.executionStack.push(originalProgram);
        this.id = setId();
    }

    public ProgramState executeOneStep() throws MyException, IOException, ADTException {
        if (executionStack.isEmpty())
            throw new MyException("Stack is empty");
        IStatement top = executionStack.pop();
        return top.execute(this);
    }

    public Boolean isNotCompleted() {
        return executionStack.isEmpty();
    }

    public void setOriginalProgram(IStatement originalPrg) {
        this.originalProgram = originalPrg;
    }

    public IStatement getCurrentProgram() {
        return this.originalProgram;
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public int getId() {
        return this.id;
    }

    public String fileTableToString() {
        StringBuilder fileTableStringBuilder = new StringBuilder();
        for (String key: fileTable.keySet()) {
            fileTableStringBuilder.append(String.format("%s\n", key));
        }
        return fileTableStringBuilder.toString();
    }

    @Override
    public String toString() {
        return  "Id\n" + id + "\n" +
                "ExecutionStack\n" + executionStack.toString() + "\n" +
                "SymbolTable\n" + symbolTable.toString() + "\n" +
                "Output\n" + output.toString() + "\n" +
                "FileTable\n" + fileTableToString() + "\n" +
                "HeapTable\n" + heap + "\n";
    }
}
