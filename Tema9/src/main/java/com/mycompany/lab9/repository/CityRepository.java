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
import com.mycompany.lab9.util.LoggerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CityRepository extends AbstractRepository<City> {

    public CityRepository(EntityManager entityManager) {
        super(entityManager, City.class);
    }

    public List<City> findCapitals() {
        long start = System.currentTimeMillis();
        try {
            TypedQuery<City> query = entityManager.createQuery(
                    "SELECT c FROM City c WHERE c.capital = true", City.class);
            List<City> results = query.getResultList();

            long end = System.currentTimeMillis();
            LoggerUtil.logExecution("CityRepository.findCapitals()", end - start);

            return results;
        } catch (Exception e) {
            LoggerUtil.logException(e);
            throw e;
        }
    }
}
