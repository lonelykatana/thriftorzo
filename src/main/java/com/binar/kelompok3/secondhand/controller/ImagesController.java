package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Images;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.service.cloudinary.ICloudinaryService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImagesController {

    private ICloudinaryService iCloudinaryService;
    private IUsersService iUsersService;

    @PostMapping("/upload-image")
    public ResponseEntity<LinkedHashMap<String, Object>> uploadImage(@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("title") String title, @RequestParam("userId") String userId) {
        String url = iCloudinaryService.uploadFile(imageFile);
        Users currentUser = iUsersService.findUsersById(Integer.valueOf(userId));
        iCloudinaryService.saveGifToDb(url, title, currentUser);

        LinkedHashMap<String, Object> jsonResponse = iCloudinaryService.modifyJsonResponse("create", url);
        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);

    }

    public ResponseEntity<LinkedHashMap<String, Object>> deleteImage(@PathVariable Integer id, @PathVariable Integer userId) {
        Users currentUser = iUsersService.findUsersById(userId);
        Images images = iCloudinaryService.findImagesByIdAndUser(id, currentUser);

        if (images == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iCloudinaryService.deleteImage(images, currentUser);
        LinkedHashMap<String, Object> jsonResponse = iCloudinaryService.modifyJsonResponse("delete", null);
        return new ResponseEntity<>(jsonResponse, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<LinkedHashMap<String, Object>> getImageById(@PathVariable Integer id) {
        Images images = iCloudinaryService.findImageById(id);

        if (images == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LinkedHashMap<String, Object> jsonResponse = iCloudinaryService.modifyJsonResponse("get", images.getImageUrl());

        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    public ResponseEntity<LinkedHashMap<String, Object>> getImageByUserId(@PathVariable Integer userId) {
        Images images = iCloudinaryService.findImagesByUsers(userId);

        if (images == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LinkedHashMap<String, Object> jsonResponse = iCloudinaryService.modifyJsonResponse("get", images.getImageUrl());

        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    public ResponseEntity<LinkedHashMap<String, Object>> updateImage(@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("title") String title, @RequestParam("userId") String userId, @RequestParam("userId") String id) {
        String url = iCloudinaryService.uploadFile(imageFile);
        iCloudinaryService.updateImage(url, title, Integer.valueOf(id));

        LinkedHashMap<String, Object> jsonResponse = iCloudinaryService.modifyJsonResponse("create", url);
        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);

    }
}
