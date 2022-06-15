package com.binar.kelompok3.secondhand.service.cities;

import com.binar.kelompok3.secondhand.model.Cities;
import com.binar.kelompok3.secondhand.repository.CitiesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CitiesServiceImpl implements ICititesService {

    private CitiesRepository citiesRepository;

    @Override
    public Cities findCitiesById(Integer id) {
        return citiesRepository.findCitiesById(id);
    }

    @Override
    public String saveCities(String name) {
        Cities cities = new Cities();
        cities.setName(name);
        citiesRepository.save(cities);
        return "Sukses";
    }

    @Override
    public String updateCities(String name, Integer id) {
        Cities cities = new Cities();
        cities.setName(name);
        cities.setId(1);
        citiesRepository.save(cities);
        return "sukses update cities";
    }

    @Override
    public List<Cities> getAllCities() {
        return citiesRepository.getAllCities();
    }
}
