package com.binar.kelompok3.secondhand.service.cloudinary;

import com.binar.kelompok3.secondhand.model.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public interface ICloudinaryService {
    String uploadFile(MultipartFile image);

    File convertMultiPartToFile(MultipartFile file) throws IOException;



}
