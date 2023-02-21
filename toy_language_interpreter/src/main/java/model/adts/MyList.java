package model.adts;


import exceptions.InterpreterException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class MyList<T> implements IList<T> {

    private List<T> list;

    public MyList(List<T> list) {
        this.list = list;
    }

    public MyList()
    {
        list = new LinkedList<>();
    }

    @Override
    public Iterator<T> iterator() {
        synchronized (list) {
            return list.iterator();
        }
    }


    @Override
    public void add(T element) {
        synchronized (list) {
            list.add(element);
        }
    }

    @Override
    public T pop() throws InterpreterException {
        synchronized (list) {
            if (this.list.size() == 0) {throw new InterpreterException("LIST ERROR: List is empty");}
            T result = list.get(0);
            list.remove(0);
            return result;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (list) {
            return list.isEmpty();
        }
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        synchronized (list) {
            list.forEach(action);
        }
    }

    @Override
    public List getList() {
        synchronized (list) {
            return list;
        }
    }

    @Override
    public void setList(List<T> list) {
        synchronized (list) {
            this.list = list;
        }
    }

    @Override
    public int getSize() {
        synchronized (list) {
            return list.size();
        }
    }

    @Override
    public T get(int i) {
        synchronized (list) {
            return this.list.get(i);
        }
    }
}
