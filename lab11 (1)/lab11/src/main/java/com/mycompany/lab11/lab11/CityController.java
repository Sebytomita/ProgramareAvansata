/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab11.lab11;

/**
 *
 * @author Seby
 */

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // GET /cities
    @GetMapping
    public List<CityORM> getAllCities() {
        return cityRepository.findAll();
    }

    // POST /cities
    @PostMapping
    public CityORM addCity(@RequestBody CityORM city) {
        return cityRepository.save(city);
    }

    // PUT /cities/{id}
    @PutMapping("/{id}")
    public CityORM updateCityName(@PathVariable Integer id, @RequestBody String newName) {
        Optional<CityORM> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            CityORM city = optionalCity.get();
            city.setName(newName);
            return cityRepository.save(city);
        } else {
            throw new RuntimeException("City not found with id " + id);
        }
    }

    // DELETE /cities/{id}
    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Integer id) {
        cityRepository.deleteById(id);
    }
}
