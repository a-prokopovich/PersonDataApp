package com.prokopovich.persondata.domain.cache;

import java.util.Collection;

public interface Cache<K, V> {

    void put(K key, V value);

    Collection<V> values();

    V get(K key);

    int size();

    boolean containsKey(K key);

    void remove(Integer key);

    boolean isEmpty();

    void clear();
}
