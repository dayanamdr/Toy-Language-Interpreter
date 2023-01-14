package domain.ADTs;

import domain.Exceptions.ADTException;
import domain.Exceptions.ExpressionException;
import domain.Exceptions.MyException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
public interface IDictionary<K,V> {
    public V lookup(K key) throws MyException;
    boolean isDefined(K key);
    void update(K key, V value);
    void delete(K key) throws MyException;
    Map<K,V> getContent();
    IDictionary<K,V> clone();
    @Override
    String toString();

    Set<K> keySet();
    Collection<V> values();

    void put(K key, V value);
}
