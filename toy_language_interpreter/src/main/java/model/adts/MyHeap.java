package model.adts;

import exceptions.InterpreterException;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<T> implements IHeap<T> {
    private Map<Integer, T> map;
    private Integer memory;

    public MyHeap() {
        this.map = new HashMap<>() ;
        this.memory = 0;
    }

    @Override
    public Integer getFreeValue() {

        synchronized (this) {return memory;}
    }

    @Override
    public Map<Integer, T> getContent() {
        synchronized (this) {
            return this.map;
        }
    }

    @Override
    public Integer allocate(T value) {
        synchronized (this) {
            memory++;
            map.put(memory, value);
            return memory;
        }
    }

    @Override
    public T deallocate(Integer position) {
        synchronized (this){return map.remove(position);}
    }

    @Override
    public void setContent(Map<Integer, T> newMap) {

        synchronized (this){this.map = newMap;}
    }

    @Override
    public void put(Integer position, T value) {
        synchronized (this) {map.put(position, value);}
    }

    @Override
    public T get(Integer position) throws InterpreterException {
        synchronized (this)
        {
            if (!map.containsKey(position)) throw new InterpreterException(String.format("ERROR: %d not present in the heap", position));
            return map.get(position);
        }
    }

    @Override
    public boolean contains_key(Integer key) {
        synchronized (this) {
            if (map.containsKey(key)) return true;
            return false;
        }
    }

    @Override
    public String toString() {
        String s = "{";
        for (Map.Entry<Integer, T> entry : this.map.entrySet()) {
            s += entry.getKey().toString() + " -> " + entry.getValue().toString() + "\n";
        }
        s += "}";
        return s;
    }


}
