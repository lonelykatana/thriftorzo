package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UsersController {

    private IUsersService iUsersService;

    @PutMapping("/update-users")
    public String updateUsers(@RequestBody Map<String, Object> user) {
        iUsersService.updateUsers((Integer) user.get("id"),user.get("name").toString() ,user.get("address").toString() ,user.get("phone").toString() ,user.get("cityName").toString() );
        return "sukses mengupdate user";
       // return "sukses mengupdate user : " + iUsersService.findUsersById(id);
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
