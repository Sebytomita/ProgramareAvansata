/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.repository;

/**
 *
 * @author Seby
 */

import com.mycompany.lab9.entity.City;
import com.mycompany.lab9.util.EntityManagerFactorySingleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class CityRepository {
    private final EntityManager entityManager;

    public CityRepository() {
        this.entityManager = EntityManagerFactorySingleton.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public void create(City city) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(city);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while creating city: " + e.getMessage(), e);
        }
    }

    public City findById(int id) {
        return entityManager.find(City.class, id);
    }

    public List<City> findByName(String namePattern) {
        TypedQuery<City> query = entityManager.createNamedQuery("City.findByName", City.class);
        query.setParameter("namePattern", namePattern);
        return query.getResultList();
    }

    public List<City> findCapitals() {
        TypedQuery<City> query = entityManager.createQuery("SELECT c FROM City c WHERE c.capital = true", City.class);
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