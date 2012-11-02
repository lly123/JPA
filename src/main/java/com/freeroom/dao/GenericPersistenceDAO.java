package com.freeroom.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GenericPersistenceDAO<T, K> {
    T find(K id);

    List<T> findAll();

    List<T> findInRange(int firstResult, int maxResults);

    @Transactional
    void persist(T entity);

    @Transactional
    T merge(T entity);

    @Transactional
    void remove(T entity);

    @Transactional
    void removeAll();

    T getReference(K id);

    long count();
}
