package model.adts;

import exceptions.InterpreterException;

import java.util.List;
import java.util.function.Consumer;

public interface IList<T> extends Iterable<T>{
    void add(T v);

    T pop() throws InterpreterException;

    boolean isEmpty();

    @Override
    void forEach(Consumer<? super T> action);

    List getList();

    void setList(List<T> list);

    int getSize();

    T get(int i);
}
