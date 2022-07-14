package com.binar.kelompok3.secondhand.service.imageproduct;

import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.model.entity.Products;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public interface IImageProductService {
    String uploadFileProduct(MultipartFile image);

    File convertMultiPartToFile(MultipartFile file) throws IOException;

    LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String url);

    void deleteImageProduct(ImageProduct imageProduct, Products currentProduct);

    ImageProduct findImageProductByUrl(String url);

    void saveImageProductToDb(String url, Products currentProduct);
}
