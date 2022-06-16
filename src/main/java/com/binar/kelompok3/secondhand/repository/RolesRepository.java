package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.users.Roles;
import com.binar.kelompok3.secondhand.enumeration.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByName(ERole name);

}
