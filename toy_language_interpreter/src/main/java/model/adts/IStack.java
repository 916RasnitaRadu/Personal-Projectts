package model.adts;

import exceptions.InterpreterException;

import java.util.Stack;


public interface IStack<T> {
    T pop() throws InterpreterException; // pop an element from the stack

    T get_top() throws InterpreterException; // gets an element from the top of the stack without removing it

    void push(T elem); // puts an element on the stack

    boolean isEmpty(); // verifies if the stack is empty

    Stack<T> get_stack(); // returns the values from the stack as a list
}
