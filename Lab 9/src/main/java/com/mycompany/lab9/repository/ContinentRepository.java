/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.repository;

/**
 *
 * @author Seby
 */

import com.mycompany.lab9.entity.Continent; 
import com.mycompany.lab9.util.EntityManagerFactorySingleton; 

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class ContinentRepository {
    private final EntityManager entityManager;

    public ContinentRepository() {
        this.entityManager = EntityManagerFactorySingleton.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public void create(Continent continent) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(continent);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while creating continent: " + e.getMessage(), e);
        }
    }

    public Continent findById(int id) {
        return entityManager.find(Continent.class, id);
    }

    public List<Continent> findByName(String namePattern) {
        TypedQuery<Continent> query = entityManager.createNamedQuery("Continent.findByName", Continent.class);
        query.setParameter("namePattern", namePattern);
        return query.getResultList();
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}