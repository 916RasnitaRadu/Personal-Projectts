package model.adts;

import exceptions.InterpreterException;

import java.util.Stack;

public class MyStack<T> implements IStack<T>{
    private Stack<T> stack;

    public MyStack()
    {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws InterpreterException {
        if (stack.isEmpty()) throw new InterpreterException("STACK ERROR: Stack is empty");
        return stack.pop();
    }

    @Override
    public T get_top() throws InterpreterException {
        if (stack.isEmpty()) throw new InterpreterException("STACK ERROR: Stack is empty");
        return stack.peek();
    }

    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        if (stack.empty()) return true;
        return false;
    }

    @Override
    public Stack<T> get_stack() {
        return stack;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder("{");
        for (T element : this.stack)
        {
            res.append(element.toString()).append("\n");
        }
        res.append("}");
        return res.toString();
    }
}
