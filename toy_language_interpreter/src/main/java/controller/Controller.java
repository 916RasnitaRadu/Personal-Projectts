package controller;

import exceptions.InterpreterException;
import model.adts.*;
import model.programState.ProgramState;
import model.statements.IStatement;
import model.values.ReferenceValue;
import model.values.Value;
import model.types.Type;
import repository.IRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.concurrent.Executors;
public class Controller {
    IRepository repository;
    private ExecutorService executor;
    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository() {
        return repository;
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) throws InterpreterException, IOException, InterruptedException {
        programStates.forEach(programState -> { // we first print the program states in the file
            try {
                repository.logProgramStateExecution(programState);
            }
            catch (IOException | InterpreterException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        });

        List<Callable<ProgramState>> callList = programStates.stream(). // prepare the execution of the callables
                map((ProgramState p) -> (Callable<ProgramState>)(() ->{return p.oneStep();}))
                .collect(Collectors.toList());

        // returns the list of new 'threads' (new created ProgramStates)
        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try
                    {
                        return future.get();
                    }
                    catch (ExecutionException | InterruptedException e)
                    {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                        return null;
                    }
                }).filter(p -> p != null).collect(Collectors.toList());
        // add the new created threads to the list of existing threads
        programStates.addAll(newProgramList);

        // add the execution report in the file
        programStates.forEach(programState -> {
            try {
                repository.logProgramStateExecution(programState);
            }
            catch (IOException | InterpreterException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        });

        repository.setProgramList(programStates); // update the repository
    }


    public void allStep() throws InterpreterException, IOException, InterruptedException {
        runTypechecker();

        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramList()); // remove the completed programs

        while (programStateList.size() > 0)
        {
            ProgramState state = programStateList.get(0);
            state.getHeap().setContent(
                    safeGarbageCollector(
                            getAddressesFromSymbolTable(
                                    state.getSymbolTable().get_content().values(),
                                    state.getHeap().getContent().values()),
                            state.getHeap().getContent()));

            oneStepForAllPrograms(programStateList); // execute one step for each programs
            programStateList = removeCompletedPrograms(repository.getProgramList()); // and remove the completed programs again
        }
        executor.shutdown();

        repository.setProgramList(programStateList);
    }

    public void displayProgramState(ProgramState state) {
        System.out.println(state.toString());
    }

    public void add(IStatement statement) {
        IStack<IStatement> executionStack = new MyStack<IStatement>();
        IDictionary<String, Value> symTable = new MyDictionary<String, Value>();
        IList<Value> output = new MyList<Value>();
        IHeap<Value> heap = new MyHeap<Value>();
        IDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        executionStack.push(statement);
        ProgramState newProgState = new ProgramState(executionStack, symTable, output, fileTable, heap);
        this.repository.addProgram(newProgState);
    }

    public void runTypechecker() throws InterpreterException
    {
        for (ProgramState prgState : getProgramStates())
        {
            IDictionary<String, Type> typeTable = new MyDictionary<>();
            prgState.getExecutionStack().get_top().typeCheck(typeTable);
        }
    }

    public List<ProgramState> getProgramStates() {
        return repository.getProgramList();
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream().filter(e -> symbolTableAddresses.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues, Collection<Value> heap) {
//        return symbolTableValues.stream().filter(v -> v instanceof ReferenceValue).
//                map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();}).collect(Collectors.toList());
        return Stream.concat(
                heap.stream().filter(v -> v instanceof ReferenceValue).map(v -> {
                    ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();
                }),
                symbolTableValues.stream().filter(v -> v instanceof ReferenceValue).map(v -> {
                    ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();
                })
        ).collect(Collectors.toList());
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> ProgramList)
    {
        return ProgramList.stream().filter(p -> !p.isCompleted()).collect(Collectors.toList());
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }
}

