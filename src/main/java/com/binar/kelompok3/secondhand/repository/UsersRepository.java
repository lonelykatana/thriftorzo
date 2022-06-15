package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
}
