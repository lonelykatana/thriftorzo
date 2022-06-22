package com.binar.kelompok3.secondhand.service.cloudinary;

import com.binar.kelompok3.secondhand.model.entity.Images;
import com.binar.kelompok3.secondhand.model.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public interface ICloudinaryService {
    String uploadFile(MultipartFile image);

    File convertMultiPartToFile(MultipartFile file) throws IOException;

    LinkedHashMap<String, Object> modifyJsonResponse(String requesType, String imageUrl);

    Images findImagesByIdAndUser(Integer id, Users currentUser);

    void deleteImage(Images images, Users currentUser);

    Images findImageById(Integer id);

    void saveGifToDb(String imageUrl, String title, Users currentUser);

    Images findImagesByUsers(Integer users);

    void updateImage(String imageUrl, String title, Integer id);


}
