package repository;

import exceptions.InterpreterException;
import model.programState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void setProgramList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws InterpreterException, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.toString());
        logFile.close();
    }

    @Override
    public ProgramState getProgramWithId(Integer id) {
        for (ProgramState p : this.programStates)
        {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public void addProgram(ProgramState newProgram) {
        programStates.add(newProgram);
    }
}
