package repository;

import exceptions.InterpreterException;
import model.programState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void setProgramList(List<ProgramState> programStates);

    List<ProgramState> getProgramList();

    void logProgramStateExecution(ProgramState programState) throws InterpreterException, IOException;

    ProgramState getProgramWithId(Integer id);

    void addProgram(ProgramState newProgram);
}
