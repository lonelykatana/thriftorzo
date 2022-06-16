package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query(value = "select * from users", nativeQuery = true)
    List<Users> getAllUsers();

    Users findUsersById(Integer id);

    String deleteUsersById(Integer id);


    Boolean existsByEmail(String email);

    Users findByEmail(String email);
}