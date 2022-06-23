package com.binar.kelompok3.secondhand.service.cloudinary;

import com.binar.kelompok3.secondhand.model.entity.Images;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.ImagesRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryServiceImpl implements ICloudinaryService {

    private Cloudinary cloudinary;
    private ImagesRepository imagesRepository;

    @Override
    public String uploadFile(MultipartFile image) {
        try {
            File uploadedFile = convertMultiPartToFile(image);
            Map params = ObjectUtils.asMap(
                    "folder", "secondhand/"
            );
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, params);
            boolean isDeleted = uploadedFile.delete();
            if (isDeleted) {
                System.out.println("File is successfully deleted");
            } else
                System.out.println("File doesn't exist");
            return uploadResult.get("url").toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @Override
    public LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String imageUrl) {
        LinkedHashMap<String, Object> jsonResponse = new LinkedHashMap<>();

        Images images = imagesRepository.findImagesByImageUrl(imageUrl);
        if (requestType.equals("create")) {
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            data.put("id", images.getId().toString());
            data.put("message", "Image successfully posted");
            data.put("title", images.getTitle());
            data.put("imageUrl", imageUrl);

            jsonResponse.put("data", data);
        }

        if (requestType.equals("delete")) {
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            data.put("message", "Image post successfully deleted");
            jsonResponse.put("data", data);
        }
        if (requestType.equals("get")) {
            jsonResponse.put("status", "success");
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put("id", images.getId().toString());
            data.put("createdOn", images.getCreatedOn().toString());
            data.put("title", images.getTitle());
            data.put("url", images.getImageUrl());

        }
        return jsonResponse;
    }

    @Override
    public Images findImagesByIdAndUser(Integer id, Users currentUser) {
        return imagesRepository.findImagesByIdAndUsers(id, currentUser);
    }

    @Override
    public void deleteImage(Images images, Users currentUser) {
        currentUser.deleteImage(images);
        imagesRepository.delete(images);
    }

    @Override
    public Images findImageById(Integer id) {
        return imagesRepository.findImagesById(id);
    }

    @Override
    public void saveImageDb(String imageUrl, String title, Users currentUser) {
        Images images = new Images();
        images.setImageUrl(imageUrl);
        images.setTitle(title);
        images.setUsers(currentUser);
        currentUser.addImage(images);
        imagesRepository.save(images);
    }

    @Override
    public Images findImagesByUsers(Integer users) {
        return imagesRepository.findImagesByUsers(users);
    }

    @Override
    public void updateImage(String imageUrl, String title, Integer id) {
         imagesRepository.updateImage(imageUrl, title, id);
    }


}
