package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.Products;
import com.binar.kelompok3.secondhand.model.users.Users;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements IProductsService {

    private ProductsRepository productsRepository;
    private IUsersService iUsersService;

    @Override
    public String saveProducts(String name, Double price, Integer status, String description, Integer userId) {
        Products products = new Products();
        products.setName(name);
        products.setPrice(price);
        products.setStatus(status);
        products.setDescription(description);
        Users users = iUsersService.findUsersById(userId);
        products.setUserId(users);
        productsRepository.save(products);
        return "sukses save produk";
    }

    @Override
    public String updateProducts(Integer id, String name, Double price, Integer status, String description, Integer userId) {
        Products products = new Products();
        products.setId(id);
        products.setName(name);
        products.setPrice(price);
        products.setStatus(status);
        products.setDescription(description);
        Users users = iUsersService.findUsersById(userId);
        products.setUserId(users);
        productsRepository.save(products);
        return "suskes update produk";
    }

    @Override
    public List<Products> getAllProducts() {
        return productsRepository.getAllProducts();
    }

    @Override
    public String deleteProductsById(Integer id) {
        productsRepository.deleteProductsById(id);
        return "suskes delete produk";
    }
}
