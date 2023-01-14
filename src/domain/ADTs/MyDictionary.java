package domain.ADTs;

import domain.Exceptions.ADTException;
import domain.Exceptions.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K,V> implements IDictionary {
    private HashMap<K,V> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<K,V>();
    }

    @Override
    public String toString() {
        String result="{";
        for (K key : dictionary.keySet())
            result += key.toString() + " -> " + dictionary.get(key).toString() + ";";
        result += "}";
        return result;
    }

    @Override
    public Object lookup(Object key) throws MyException {
        if (!dictionary.containsKey((K) key))
            throw new MyException("Key doesn't exist.");
        return dictionary.get((K) key);
    }

    @Override
    public boolean isDefined(Object key) {
        return dictionary.containsKey((K)key);
    }

   @Override
    public void update(Object key, Object value){
//       if (!isDefined(key))
//           throw new ADTException(key + " is not defined");
        dictionary.put((K) key, (V) value);
    }

    @Override
    public void delete(Object key) throws MyException {
        if (!dictionary.containsKey((K)key))
            throw new MyException("Key does not exist.");
        dictionary.remove((K)key);
    }

    @Override
    public Map getContent() {
        return dictionary;
    }

    @Override
    public IDictionary<K,V> clone(){
        IDictionary<K,V> copy = new MyDictionary<>();
        for (K k : dictionary.keySet()) {
            copy.update(k, dictionary.get(k));
        }
        return copy;
    }

    @Override
    public Set<K> keySet() {
        return dictionary.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.dictionary.values();
    }

    @Override
    public void put(Object key, Object value) {
        this.dictionary.put((K)key, (V)value);
    }
}
