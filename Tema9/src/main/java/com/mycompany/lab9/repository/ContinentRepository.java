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
import com.mycompany.lab9.util.LoggerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ContinentRepository extends AbstractRepository<Continent> {

    public ContinentRepository(EntityManager entityManager) {
        super(entityManager, Continent.class);
    }

    // Metodă personalizată, dacă vrei, poți include și logare suplimentară:
    @Override
    public List<Continent> findByName(String namePattern) {
        long start = System.currentTimeMillis();
        try {
            TypedQuery<Continent> query = entityManager.createNamedQuery("Continent.findByName", Continent.class);
            query.setParameter("namePattern", "%" + namePattern + "%");
            List<Continent> results = query.getResultList();

            long end = System.currentTimeMillis();
            LoggerUtil.logExecution("ContinentRepository.findByName", end - start);

            return results;
        } catch (Exception e) {
            LoggerUtil.logException(e);
            throw e;
        }
    }
}
