package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends JpaRepository<Cities, Integer> {
}
