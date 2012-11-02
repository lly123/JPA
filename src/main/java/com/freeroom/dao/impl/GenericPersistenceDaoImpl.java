package com.freeroom.dao.impl;

import com.freeroom.dao.GenericPersistenceDAO;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericPersistenceDaoImpl<T, K> implements GenericPersistenceDAO<T, K> {
    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericPersistenceDaoImpl() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            this.entityClass = (Class<T>) ((ParameterizedType) ((Class) getClass()
                    .getGenericSuperclass()).getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    public Class<T> getPersistentClass() {
        return entityClass;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public T find(K id) {
        return entityManager.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<T> findAll() {
        StringBuilder queryStr = new StringBuilder(256);
        queryStr.append("select distinct t from ").
                append(entityClass.getSimpleName()).append(" t");

        final Query q = this.entityManager.createQuery(queryStr.toString());
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<T> findInRange(int firstResult, int maxResults) {
        StringBuilder queryStr = new StringBuilder(256);
        queryStr.append("select distinct t from ").
                append(entityClass.getSimpleName()).append(" t");

        final Query q = this.entityManager.createQuery(queryStr.toString()).
                setFirstResult(firstResult).setMaxResults(maxResults);

        return q.getResultList();
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    public void remove(T entity) {
        entityManager.remove(entity);
    }

    public T getReference(K id) {
        return entityManager.getReference(entityClass, id);
    }

    public long count() {
        Query q = entityManager.createQuery(
                "select count(t) from " + entityClass.getSimpleName() + " t");
        return (Long) q.getSingleResult();
    }

    public void removeAll() {
        Query query = entityManager.createQuery(
                "delete from " + entityClass.getSimpleName());
        query.executeUpdate();
        return;
    }
}


