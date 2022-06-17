package com.binar.kelompok3.secondhand.service.users;

import com.binar.kelompok3.secondhand.model.Users;

import java.util.List;

public interface IUsersService {

    Integer updateUsers(Integer id, String name, String address, String phone, String cityName);

    Integer updatePassword(Integer id, String password);

    List<Users> getAllUsers();

    String deleteUsersById(Integer id);

    Users findUsersById(Integer id);
}
