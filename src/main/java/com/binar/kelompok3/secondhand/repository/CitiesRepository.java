package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitiesRepository extends JpaRepository<Cities, Integer> {

    Cities findCitiesById(Integer id);
    @Query(value ="select * from cities" ,nativeQuery = true)
    List<Cities> getAllCities();
}
