package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.request.UpdateUserRequest;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersControllerTest {

    @Autowired
    UsersController usersController;

    @Autowired
    UsersRepository usersRepository;

    /*@Test
    void updateUsers() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(1, "kambing1", "address baru", "1003", "Bekasi");
        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK), usersController.updateUsers(updateUserRequest));

    }*/
}