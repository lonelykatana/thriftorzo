package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductsController {

    private IProductsService iProductsService;


    public String addProducts(String name, Double price, Integer status, String description, Integer userId) {
        iProductsService.saveProducts(name, price, status, description, userId);
        return "sukses menambah produk";
    }

    public String updateProducts(String name, Double price, Integer status, String description, Integer id) {
        iProductsService.updateProducts(name, price, status, description, id);
        return "sukses mengupdate produk : " + iProductsService.findProductsById(id);
    }

    public List<Products> getAllProducts() {
        return iProductsService.getAllProducts();

    }

    public String deleteProducts(Integer id) {
        iProductsService.deleteProductsById(id);
        return "sukses menghapus produk : " + iProductsService.findProductsById(id);
    }

    public Products findProducts(Integer id) {
        return iProductsService.findProductsById(id);
    }

}
