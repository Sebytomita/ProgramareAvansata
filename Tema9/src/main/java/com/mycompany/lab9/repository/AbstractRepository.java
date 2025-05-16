/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.repository;

/**
 *
 * @author Seby
 */

import com.mycompany.lab9.util.LoggerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractRepository<T> {
    protected final EntityManager entityManager;  // ← Asta e cheia
    private final Class<T> entityClass;

    public AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            LoggerUtil.logException(e);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Error persisting entity", e);
        }
    }

    public T findById(int id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findByName(String namePattern) {
        long start = System.currentTimeMillis();
        try {
            TypedQuery<T> query = entityManager.createNamedQuery(
                    entityClass.getSimpleName() + ".findByName", entityClass);
            query.setParameter("namePattern", "%" + namePattern + "%");
            List<T> results = query.getResultList();

            long end = System.currentTimeMillis();
            LoggerUtil.logExecution(entityClass.getSimpleName() + ".findByName", end - start);
            return results;
        } catch (Exception e) {
            LoggerUtil.logException(e);
            throw e;
        }
    }
}

