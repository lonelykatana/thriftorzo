package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.Products;
import com.binar.kelompok3.secondhand.model.Users;
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
    public Integer updateProducts(String name, Double price, Integer status, String description, Integer id) {
        productsRepository.updateProducts(name, price, status, description, id);
        return 1; //1 untuk sukses
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

    @Override
    public Products findProductsById(Integer id) {
        return productsRepository.findProductsById(id);
    }
}
