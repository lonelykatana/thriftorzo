package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.request.UpdatePasswordRequest;
import com.binar.kelompok3.secondhand.model.request.UpdateUserRequest;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {

    private IUsersService iUsersService;

    @GetMapping("/get-user/{id}")
    public ResponseEntity<Users> getUser(@PathVariable("id") Integer id) {
        Users user = iUsersService.findUsersById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = iUsersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update-data/{id}")
    public ResponseEntity<HttpStatus> updateUsers(@PathVariable("id") Integer id,
                                                  @Valid @RequestBody UpdateUserRequest request) {
        iUsersService.updateUsers(id, request.getName(), request.getAddress(), request.getPhone(), request.getCityName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<HttpStatus> updatePassword(@PathVariable("id") Integer id,
                                                     @RequestBody UpdatePasswordRequest request) {
        iUsersService.updatePassword(id, request.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Integer id) {
        iUsersService.deleteUsersById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public Users getUsersAndImgUrl(Integer id){
        return iUsersService.getUsersAndImgUrl(id);
    }
}
