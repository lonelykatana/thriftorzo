package com.binar.kelompok3.secondhand.service.users;

import com.binar.kelompok3.secondhand.model.entity.Users;

import java.util.List;

public interface IUsersService {

    void updateUsers(Integer id, String name, String address, String phone, String cityName);

    void updatePassword(Integer id, String password);

    List<Users> getAllUsers();

    void deleteUsersById(Integer id);

    Users findUsersById(Integer id);

    Users getUsersAndImgUrl(Integer id);
}
