package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements IProductsService {

    private ProductsRepository productsRepository;
    private IUsersService iUsersService;

    @Override
    public void saveProducts(String name, Double price, Integer status, String description, Integer userId) {
        Products products = new Products();
        products.setName(name);
        products.setPrice(price);
        products.setStatus(status);
        products.setDescription(description);
        Users users = iUsersService.findUsersById(userId);
        products.setUserId(users);
        productsRepository.save(products);
    }

    @Override
    public void updateProducts(String name, Double price, Integer status, String description, Integer id) {
        productsRepository.updateProducts(name, price, status, description, id);
    }


    @Override
    public List<Products> getAllProducts() {
        return productsRepository.getAllProducts();
    }

    @Override
    public Page<Products> getAllProductsPaginated(Pageable pageable) {
        return productsRepository.getAllProductsPaginated(pageable);
    }

    @Override
    public Page<Products> searchProductByNamePaginated(String productName, Pageable pageable) {
        return productsRepository.findProductsByNameContainingIgnoreCase(productName, pageable);
    }

    @Override
    public Page<Products> filterProductByCategoryPaginated(String category, Pageable pageable) {
        return productsRepository.findProductsByCategoryContainingIgnoreCase(category, pageable);
    }

    @Override
    public void deleteProductsById(Integer id) {
        productsRepository.deleteProductsById(id);
    }

    @Override
    public Products findProductsById(Integer id) {
        return productsRepository.findProductsById(id);
    }
}
