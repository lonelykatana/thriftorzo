package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.request.UpdatePasswordRequest;
import com.binar.kelompok3.secondhand.model.request.UpdateUserRequest;
import com.binar.kelompok3.secondhand.model.response.user.UserResponse;
import com.binar.kelompok3.secondhand.service.cloudinary.ICloudinaryService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {

    private IUsersService iUsersService;
    private ICloudinaryService iCloudinaryService;

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

    @GetMapping("/get-user-test")
    public ResponseEntity<UserResponse> getUserByToken(Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());
        UserResponse userResponse = new UserResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/update-data/{id}")
    public ResponseEntity<HttpStatus> updateUsers(@PathVariable("id") Integer id,
                                                  @Valid @RequestBody UpdateUserRequest request) {
        iUsersService.updateUsers(id, request.getName(), request.getAddress(), request.getPhone(),
                request.getCityName(), request.getImgUrl());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-data-test")
    public ResponseEntity<HttpStatus> updateUsersAuth(Authentication authentication,
                                                      @Valid @RequestBody UpdateUserRequest request) {
        Users user = iUsersService.findByEmail(authentication.getName());
        iUsersService.updateUsers(user.getId(), request.getName(), request.getAddress(), request.getPhone(),
                request.getCityName(), request.getImgUrl());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<HttpStatus> updatePassword(@PathVariable("id") Integer id,
                                                     @RequestBody UpdatePasswordRequest request) {
        iUsersService.updatePassword(id, request.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-password-test")
    public ResponseEntity<HttpStatus> updatePasswordAuth(Authentication authentication,
                                                         @RequestBody UpdatePasswordRequest request) {
        String name = authentication.getName();
        Users user = iUsersService.findByEmail(name);
        iUsersService.updatePassword(user.getId(), request.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

/*
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Integer id) {
        iUsersService.deleteUsersById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-user-test")
    public ResponseEntity<HttpStatus> deleteUserAuth(Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());
        iUsersService.deleteUsersById(user.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
*/


    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        String url = iCloudinaryService.uploadFile(imageFile);
        return new ResponseEntity<>(url, HttpStatus.CREATED);

    }
}
