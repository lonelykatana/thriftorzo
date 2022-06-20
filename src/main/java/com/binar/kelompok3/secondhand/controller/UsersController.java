package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class UsersController {

    private IUsersService iUsersService;

    public String updateUsers(Integer id, String name, String address, String phone, String cityName) {
        iUsersService.updateUsers(id, name, address, phone, cityName);
        return "sukses mengupdate user : " + iUsersService.findUsersById(id);
    }

    public String updatePassword(Integer id, String password) {
        iUsersService.updatePassword(id, password);
        return "sukses mengubah password : " + iUsersService.findUsersById(id);
    }

    public List<Users> getAllUsers() {
        return iUsersService.getAllUsers();
    }

    public String deleteUser(Integer id) {
        iUsersService.deleteUsersById(id);
        return "sukses menghapus user : " + iUsersService.findUsersById(id);
    }

    public Users getUser(Integer id) {
        return iUsersService.findUsersById(id);
    }
}
