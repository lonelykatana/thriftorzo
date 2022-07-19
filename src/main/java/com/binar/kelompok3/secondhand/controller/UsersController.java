package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.request.UpdatePasswordRequest;
import com.binar.kelompok3.secondhand.model.request.UpdateUserRequest;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.user.UserResponse;
import com.binar.kelompok3.secondhand.service.cloudinary.ICloudinaryService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "/user", tags = "User")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {

    private IUsersService iUsersService;
    private ICloudinaryService iCloudinaryService;
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Get a user.")
    @GetMapping("/get")
    public ResponseEntity<UserResponse> getUserByToken(Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());

        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserResponse userResponse = new UserResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Update user data.")
    @PutMapping("/update-data")
    public ResponseEntity<MessageResponse> updateUsersAuth(Authentication authentication,
                                                           @Valid @RequestBody UpdateUserRequest request) {
        Users user = iUsersService.findByEmail(authentication.getName());
        iUsersService.updateUsers(user.getId(), request.getName(), request.getAddress(), request.getPhone(),
                request.getCityName(), request.getImgUrl());
        return ResponseEntity.ok(new MessageResponse("User '" + user.getEmail() + "' Updated!"));
    }

    @ApiOperation(value = "Change user password.")
    @PutMapping("/password")
    public ResponseEntity<MessageResponse> updatePasswordAuth(Authentication authentication,
                                                              @RequestBody UpdatePasswordRequest request) {
        String name = authentication.getName();
        Users user = iUsersService.findByEmail(name);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return new ResponseEntity<>(new MessageResponse("Wrong Password."), HttpStatus.NOT_ACCEPTABLE);
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return new ResponseEntity<>(new MessageResponse("Password Confirmation Mismatched."), HttpStatus.NOT_ACCEPTABLE);
        }

        iUsersService.updatePassword(user.getId(), request.getNewPassword());
        return new ResponseEntity<>(new MessageResponse("Password Changed Successfully."), HttpStatus.OK);
    }

    @ApiOperation(value = "Upload user profile image.")
    @PostMapping("/avatar")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        String url = iCloudinaryService.uploadFile(imageFile);
        return new ResponseEntity<>(url, HttpStatus.CREATED);
    }
}
