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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContinentRepositoryTest {
    private ContinentRepository continentRepository;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        continentRepository = new ContinentRepository();
        entityManager = continentRepository.getEntityManager();
     
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DELETE FROM cities").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM countries").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM continents").executeUpdate();
        entityManager.createNativeQuery("DROP SEQUENCE cities_seq").executeUpdate();
        entityManager.createNativeQuery("CREATE SEQUENCE cities_seq START WITH 1 INCREMENT BY 1").executeUpdate();
        entityManager.createNativeQuery("DROP SEQUENCE countries_seq").executeUpdate();
        entityManager.createNativeQuery("CREATE SEQUENCE countries_seq START WITH 1 INCREMENT BY 1").executeUpdate();
        entityManager.createNativeQuery("DROP SEQUENCE continents_seq").executeUpdate();
        entityManager.createNativeQuery("CREATE SEQUENCE continents_seq START WITH 1 INCREMENT BY 1").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @AfterEach
    public void tearDown() {
        if (continentRepository != null) {
            continentRepository.close();
        }
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

        List<Continent> continents = continentRepository.findByName("%Eur%");
        assertEquals(1, continents.size());
        assertEquals("Europe", continents.get(0).getName());
    }
}