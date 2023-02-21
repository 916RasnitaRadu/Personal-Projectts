package model.adts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1, T2> implements IDictionary<T1, T2>{
    private HashMap<T1, T2> dict;

    public MyDictionary()
    {
        dict = new HashMap<>();
    }

    @Override
    public void put(T1 k, T2 v) {
        synchronized (dict) {
            dict.put(k, v);
        }
    }

    @Override
    public T2 get(T1 k) {
        synchronized (dict) {
            return dict.get(k);
        }

    }

    @Override
    public boolean contains_key(T1 k) {
        synchronized (dict) {
            return dict.containsKey(k);
        }
    }

    @Override
    public Set<T1> get_key_set() {
        synchronized (dict) {
            return dict.keySet();
        }
    }

    @Override
    public void remove(T1 k) {
        synchronized (dict) {
            dict.remove(k);
        }
    }

    @Override
    public Map<T1, T2> get_content() {
        synchronized (dict) {
            return dict;
        }
    }

    @Override
    public IDictionary copy() {
        IDictionary<T1, T2> copy_dict = new MyDictionary<>();
        for (T1 key : dict.keySet())
            copy_dict.put(key, dict.get(key));
        return copy_dict;
    }
}
