package com.binar.kelompok3.secondhand.service.cities;

import com.binar.kelompok3.secondhand.model.Cities;

import java.util.List;

public interface ICititesService {

    Cities findCitiesById(Integer id);

    String saveCities(String name);

    String updateCities(String name, Integer id);

    List<Cities> getAllCities();
}
