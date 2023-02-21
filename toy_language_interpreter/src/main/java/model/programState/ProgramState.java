package model.programState;

import exceptions.InterpreterException;
import model.adts.*;
import model.statements.IStatement;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.util.TreeSet;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, Value> symbolTable;
    private IDictionary<String, BufferedReader> fileTable;
    private IList<Value> output;
    private IHeap<Value> heap;
    private static final TreeSet<Integer> ids = new TreeSet<>();
    public final Integer id;

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, IList<Value> output, IDictionary<String, BufferedReader> fileTable, IHeap<Value> heap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        id = newId();
    }

    public ProgramState(IStatement originalProgram) {
        executionStack = new MyStack<>();
        symbolTable = new MyDictionary<>();
        output = new MyList<>();
        fileTable = new MyDictionary<>();
        heap = new MyHeap<>();
        executionStack.push(originalProgram);
        id = newId();
    }

    private static Integer newId()
    {
        Random random = new Random();
        Integer id;
        synchronized (ids)
        {
            do
            {
                id = random.nextInt() % 100 + 1;
            } while (ids.contains(id));
            ids.add(id);
        }
        return id;
    }

    public boolean isCompleted() {
        return executionStack.isEmpty(); // verifies if the program is completed aka the execution stack is empty
    }

    public ProgramState oneStep() throws InterpreterException, IOException {

        if (executionStack.isEmpty()) {
            throw new InterpreterException("ERROR: Program State stack is empty.");
        }
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }


    public String outToString() {
        StringBuilder outputStringBuilder = new StringBuilder();
        for (Value obj : output) {
            outputStringBuilder.append(obj.toString()).append("\n");
        }
        return outputStringBuilder.toString();
    }

    public String symbolTableToString() {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key : this.symbolTable.get_key_set()) {
            symTableStringBuilder.append(String.format("%s : %s\n", key, symbolTable.get(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String fileTableToString()
    {
        StringBuilder fileTableToStringBuilder = new StringBuilder();
        for (String obj : fileTable.get_key_set())
        {
            fileTableToStringBuilder.append(obj).append("\n");
        }
        return fileTableToStringBuilder.toString();
    }

    public String exeStackToString() {
        return executionStack.toString();
    }

    public String HeapToString() { return heap.toString();}

    public Integer getId() { return this.id;}

    public IStack<IStatement> getExecutionStack() { return executionStack;}

    public void setExecutionStack(IStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(IDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IHeap<Value> getHeap() { return heap;}

    public void setHeap(IHeap<Value> newHeap) { this.heap = newHeap;}

    public IList<Value> getOutput() {
        return output;
    }

    public void setOutput(IList<Value> output) {
        this.output = output;
    }

    public IDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    @Override
    public String toString() {
        return String.format("======ID======\n%d\n======EXE_STACK======\n%s\n======SYM_TABLE======\n%s======OUT======\n%s======FILE_TABLE======\n%s======HEAP======\n%s",
                this.id,
                exeStackToString(),
                symbolTableToString(),
                outToString(),
                fileTableToString(),
                HeapToString()
        );
    }

    private IStatement deepCopy(IStatement statement)
    {
        IList<Value> out2 = new MyList<>();
        for (int i = 0 ; i < output.getSize(); i++)
        {
            out2.add(output.get(i));
        }

        return null;
    }
}
