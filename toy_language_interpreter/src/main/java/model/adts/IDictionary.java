package model.adts;

import java.util.Map;
import java.util.Set;

public interface IDictionary<T1, T2> {
    void put(T1 k, T2 v); // puts a new value in the dictionary

    T2 get(T1 k); // gets the value with the key k

    boolean contains_key(T1 k);

    Set<T1> get_key_set(); // returns the set of keys from the dictionary

    void remove(T1 k); // removes the element with the key k

    Map<T1, T2> get_content(); // returns a map <T1, T2> with the values from the dictionary

    IDictionary<T1, T2> copy();
}
