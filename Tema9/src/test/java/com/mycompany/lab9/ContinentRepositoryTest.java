package com.mycompany.lab9;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Seby
 */

import com.mycompany.lab9.entity.Continent;
import com.mycompany.lab9.repository.ContinentRepository;
import com.mycompany.lab9.util.EntityManagerFactorySingleton;
import com.mycompany.lab9.util.LoggerUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContinentRepositoryTest {

    private ContinentRepository continentRepository;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        continentRepository = new ContinentRepository(entityManager);

        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.createNativeQuery("DELETE FROM cities").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM countries").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM continents").executeUpdate();

            entityManager.createNativeQuery("DROP SEQUENCE IF EXISTS cities_seq").executeUpdate();
            entityManager.createNativeQuery("CREATE SEQUENCE cities_seq START WITH 1 INCREMENT BY 1").executeUpdate();
            entityManager.createNativeQuery("DROP SEQUENCE IF EXISTS countries_seq").executeUpdate();
            entityManager.createNativeQuery("CREATE SEQUENCE countries_seq START WITH 1 INCREMENT BY 1").executeUpdate();
            entityManager.createNativeQuery("DROP SEQUENCE IF EXISTS continents_seq").executeUpdate();
            entityManager.createNativeQuery("CREATE SEQUENCE continents_seq START WITH 1 INCREMENT BY 1").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            LoggerUtil.logException(e);
            if (tx.isActive()) tx.rollback();
        }
    }

    @AfterEach
    public void tearDown() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        EntityManagerFactorySingleton.getInstance().close(); // închide factory dacă vrei
    }

    @Test
    public void testCreateAndFindById() {
        Continent europe = new Continent("Europe");
        continentRepository.create(europe);

        Continent found = continentRepository.findById(europe.getId());
        assertNotNull(found);
        assertEquals("Europe", found.getName());
    }

    @Test
    public void testFindByName() {
        continentRepository.create(new Continent("Europe"));
        continentRepository.create(new Continent("Asia"));

        List<Continent> continents = continentRepository.findByName("Eur");
        assertEquals(1, continents.size());
        assertEquals("Europe", continents.get(0).getName());
    }
}
