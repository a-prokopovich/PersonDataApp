package com.prokopovich.persondata.domain.cache;

import com.prokopovich.persondata.domain.model.Person;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonListCache implements Cache<Integer, Person> {

    private final int CACHE_SIZE;
    private Map<Integer, Person> cache;

    public PersonListCache(int cacheSize) {
        this.CACHE_SIZE = cacheSize;
        this.cache = new LinkedHashMap<>(CACHE_SIZE);
    }

    @Override
    public void put(Integer key, Person value) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        } else {
            if (cache.size() >= CACHE_SIZE) {
                cache.remove(cache.keySet().iterator().next());
            }
        }
        cache.put(key, value);
    }

    @Override
    public Person get(Integer key) {
        return cache.get(key);
    }

    @Override
    public void remove(Integer key) {
        cache.remove(key);
    }

    @Override
    public Collection<Person> values() {
        return cache.values();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean containsKey(Integer key) {
        return cache.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
