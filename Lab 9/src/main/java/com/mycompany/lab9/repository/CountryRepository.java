/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.repository;

/**
 *
 * @author Seby
 */

import com.mycompany.lab9.entity.Country;
import com.mycompany.lab9.util.EntityManagerFactorySingleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class CountryRepository {
    private final EntityManager entityManager;

    public CountryRepository() {
        this.entityManager = EntityManagerFactorySingleton.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public void create(Country country) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(country);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while creating country: " + e.getMessage(), e);
        }
    }

    public Country findById(int id) {
        return entityManager.find(Country.class, id);
    }

    public List<Country> findByName(String namePattern) {
        TypedQuery<Country> query = entityManager.createNamedQuery("Country.findByName", Country.class);
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
