package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query(value = "update users set name=?2, alamat=?3, phone=?4, city_name=?5 where id=?1", nativeQuery = true)
    Integer updateUsers(Integer id, String name, String address, String phone, String cityName);

    @Modifying
    @Query(value = "update users set password=?2 where id=?1", nativeQuery = true)
    Integer updatePassword(Integer id, String password);

    Boolean existsByEmail(String email);

    Users findByEmail(String email);

}