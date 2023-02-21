package model.adts;


import exceptions.InterpreterException;

import java.util.Map;

public interface IHeap<T> {
    Integer getFreeValue();

    Map<Integer, T> getContent();

    Integer allocate(T value);

    T deallocate(Integer position);

    void setContent(Map<Integer, T> newMap);

    void put(Integer position, T value);

    T get(Integer position) throws InterpreterException;

    boolean contains_key(Integer key);
}
