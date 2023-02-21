package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.Type;

public class PrintStatement implements IStatement{
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        state.getOutput().add(
                expression.eval(state.getSymbolTable(), state.getHeap())
        );
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        expression.typeCheck(typeTable);
        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Print{%s}",expression.toString());
    }
}
