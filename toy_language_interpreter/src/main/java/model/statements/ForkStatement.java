package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IStack;
import model.adts.MyStack;
import model.programState.ProgramState;
import model.types.Type;

import java.io.IOException;

public class ForkStatement implements IStatement{
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    public IStatement getStatement() {
        return statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, IOException {
        IStack<IStatement> newExecutionStack = new MyStack<>();
        newExecutionStack.push(statement);
        return new ProgramState(newExecutionStack, state.getSymbolTable().copy(),state.getOutput(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        statement.typeCheck(typeTable.copy());
        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("ForkStatement{ %s }", statement.toString());
    }
}
