package com.freeroom.dao.impl;

import com.freeroom.domain.UniqueNameEntity;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public abstract class UniqueNameEntityDaoImpl<T extends UniqueNameEntity, K extends Serializable>
        extends GenericPersistenceDaoImpl<T, K> {

    protected T findByName(String name) {
        StringBuilder queryStr = new StringBuilder(256);
        queryStr.append("select distinct t from ").
                append(entityClass.getSimpleName()).append(" t ").
                append("where t.name = :name");

        final Query q = this.entityManager.createQuery(queryStr.toString());
        q.setParameter("name", name);

        T retVal = null;
        try {
            retVal = (T) q.getSingleResult();
        } catch (Exception e) {
        }

        return retVal;
    }

    protected List<T> findByNames(List<String> names) {
        StringBuilder queryStr = new StringBuilder(256);
        queryStr.append("select distinct t from ").
                append(entityClass.getSimpleName()).append(" t ").
                append("where t.name in (:namesList)");

        Query q = this.entityManager.createQuery(queryStr.toString());
        q.setParameter("namesList", names);

        return q.getResultList();
    }
}
