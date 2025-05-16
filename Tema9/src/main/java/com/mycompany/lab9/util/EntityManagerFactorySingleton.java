/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.util;

/**
 *
 * @author Seby
 */


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {

    private static EntityManagerFactorySingleton instance;
    private final EntityManagerFactory entityManagerFactory;

    private EntityManagerFactorySingleton() {
        // Numele trebuie să corespundă cu cel din persistence.xml
        this.entityManagerFactory = Persistence.createEntityManagerFactory("lab9PU");
    }

    public static synchronized EntityManagerFactorySingleton getInstance() {
        if (instance == null) {
            instance = new EntityManagerFactorySingleton();
        }
        return instance;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public javax.persistence.EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
