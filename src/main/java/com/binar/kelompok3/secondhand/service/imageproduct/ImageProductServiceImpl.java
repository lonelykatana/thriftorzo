package com.binar.kelompok3.secondhand.service.imageproduct;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.repository.ImageProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@AllArgsConstructor
    public class ImageProductServiceImpl implements IImageProductService {

    private Cloudinary cloudinary;
    private ImageProductRepository imageProductRepository;


    @Override
    public String uploadFileProduct(MultipartFile image) {
        try {
            File uploadedFile = convertMultiPartToFile(image);
            Map params = ObjectUtils.asMap(
                    "folder", "secondhand/product/"
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
    public LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String url) {
        LinkedHashMap<String, Object> jsonResponse = new LinkedHashMap<>();
        ImageProduct imageProduct = imageProductRepository.findImageProductByUrl(url);
        if (requestType.equals("create")) {
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            data.put("message", "image successfully posted");
            data.put("createdOn", imageProduct.getCreatedOn().toString());
            data.put("url", url);

            jsonResponse.put("data", data);
        }

        if (requestType.equals("delete")) {
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            data.put("message", "image post successfully deleted");
            jsonResponse.put("data", data);
        }

        if (requestType.equals("get")) {

            jsonResponse.put("status", "success");
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();

            data.put("createdOn", imageProduct.getCreatedOn().toString());
            data.put("url", imageProduct.getUrl());


        }

        //look at the else condition again if need be. for better error handling.


        return jsonResponse;
    }

    @Override
    public void deleteImageProduct(ImageProduct imageProduct, Products currentProduct) {
        currentProduct.deleteImageProduct(imageProduct);
        imageProductRepository.delete(imageProduct);
    }

    @Override
    public ImageProduct findImageProductByUrl(String url) {
        return imageProductRepository.findImageProductByUrl(url);
    }

    @Override
    public void saveImageProductToDb(String url, Products currentProduct) {
        ImageProduct imageProduct = new ImageProduct();
        imageProduct.setUrl(url);
        imageProduct.setProducts(currentProduct);
        currentProduct.add(imageProduct);
        imageProductRepository.save(imageProduct);
    }



}
