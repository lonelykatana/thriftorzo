package com.binar.kelompok3.secondhand.service.users;

import com.binar.kelompok3.secondhand.model.users.Users;

import java.util.List;

public interface IUsersService {

    void updateUsers(Integer id, String name, String address, String phone, Integer cityId);

    List<Users> getAllUsers();

    String deleteUsersById(Integer id);

    Users findUsersById(Integer id);
}
