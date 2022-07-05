package com.binar.kelompok3.secondhand.controller;


import com.binar.kelompok3.secondhand.model.entity.ImageProduct;
import com.binar.kelompok3.secondhand.service.imageproduct.IImageProductService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImageProductController {

    private IProductsService iProductsService;
    private IImageProductService iImageProductService;

    // id yg dimaksud adalah id product
    @PostMapping("/upload-products-image")
    public ResponseEntity<List<String>> uploadImage(@RequestParam("imageFiles") MultipartFile[] imageFiles) {
        List<String> urls = new ArrayList<>();
        Arrays.stream(imageFiles)
                .forEach(imageFile -> urls.add(iImageProductService.uploadFileProduct(imageFile)));
        return new ResponseEntity<>(urls, HttpStatus.CREATED);
    }

    @GetMapping("/image/{url}")
    public ResponseEntity<LinkedHashMap<String, Object>> getASpecificGif(@PathVariable String url) {
        ImageProduct imageProduct = iImageProductService.findImageProductByUrl(url);

        if (imageProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LinkedHashMap<String, Object> jsonResponseSpec = iImageProductService.modifyJsonResponse("get", url);

        return new ResponseEntity<>(jsonResponseSpec, HttpStatus.OK);
    }

}
