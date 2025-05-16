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
import com.mycompany.lab9.util.LoggerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CountryRepository extends AbstractRepository<Country> {

    public CountryRepository(EntityManager entityManager) {
        super(entityManager, Country.class);
    }

    @Override
    public List<Country> findByName(String namePattern) {
        long start = System.currentTimeMillis();
        try {
            TypedQuery<Country> query = entityManager.createNamedQuery("Country.findByName", Country.class);
            query.setParameter("namePattern", "%" + namePattern + "%");
            List<Country> results = query.getResultList();

            long end = System.currentTimeMillis();
            LoggerUtil.logExecution("CountryRepository.findByName", end - start);

            return results;
        } catch (Exception e) {
            LoggerUtil.logException(e);
            throw e;
        }
    }
}
