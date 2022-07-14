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

    @GetMapping("/get-all-users")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = iUsersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserResponse> getUserByToken(Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());
        UserResponse userResponse = new UserResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/update-data")
    public ResponseEntity<HttpStatus> updateUsersAuth(Authentication authentication,
                                                      @Valid @RequestBody UpdateUserRequest request) {
        Users user = iUsersService.findByEmail(authentication.getName());
        iUsersService.updateUsers(user.getId(), request.getName(), request.getAddress(), request.getPhone(),
                request.getCityName(), request.getImgUrl());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<HttpStatus> updatePasswordAuth(Authentication authentication,
                                                         @RequestBody UpdatePasswordRequest request) {
        String name = authentication.getName();
        Users user = iUsersService.findByEmail(name);
        iUsersService.updatePassword(user.getId(), request.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        String url = iCloudinaryService.uploadFile(imageFile);
        return new ResponseEntity<>(url, HttpStatus.CREATED);

    }
}
