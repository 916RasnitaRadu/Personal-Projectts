package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.programState.ProgramState;
import model.types.Type;

public class NoOperationStatement implements IStatement{

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public String toString() {
        return "NoOperationStatement{}";
    }
}
