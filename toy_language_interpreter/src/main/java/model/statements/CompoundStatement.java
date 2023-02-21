package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IStack;
import model.programState.ProgramState;
import model.types.Type;

public class CompoundStatement implements IStatement {
    private IStatement statement1;
    private IStatement statement2;

    public CompoundStatement(IStatement statement1, IStatement statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }


    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IStack<IStatement> stack = state.getExecutionStack();
        stack.push(statement2);
        stack.push(statement1);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        return statement2.typeCheck(statement1.typeCheck(typeTable));
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(statement1.deepCopy(), statement2.deepCopy());
    }

    @Override
    public String toString()
    {
        return String.format("%s; %s", statement1, statement2);
    }
}
