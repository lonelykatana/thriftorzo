package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;


@Service
@AllArgsConstructor
public class ProductsServiceImpl implements IProductsService {

    private ProductsRepository productsRepository;
    private IUsersService iUsersService;

    @Override
    public void saveProducts(String id, String name, Double price, Integer status, Integer publish, String description,
                             String category, Integer userId) {
        Products products = new Products();
        products.setId(id);
        products.setName(name);
        products.setPrice(price);
        products.setStatus(status);
        products.setPublish(publish);
        products.setDescription(description);
        products.setCategory(category);
        Users users = iUsersService.findUsersById(userId);
        products.setUserId(users);
        productsRepository.save(products);
    }


    @Override
    public void updateProducts(String name, Double price, Integer status, Integer publish, String description,
                               String category, String id) {
        productsRepository.updateProducts(name, price, status, publish, description, category, id);
    }

    @Override
    public Page<Products> getAllProductsPaginated(Pageable pageable) {
        return productsRepository.findAllByOrderByCreatedOnDesc(pageable);
    }

    @Override
    public Page<Products> getAllProductPublishPaginated(Pageable pageable) {
        return productsRepository.getAllProductReadyPaginated(pageable);
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
    public void deleteProductsById(String id) {
        productsRepository.deleteProductsById(id);
    }

    @Override
    public Products findProductsById(String id) {
        return productsRepository.findProductsById(id);
    }

    @Override
    public LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String id) {
        LinkedHashMap<String, Object> jsonResponse = new LinkedHashMap<>();
        Products products = productsRepository.findProductsById(id);


        if (requestType.equals("get")) {
            jsonResponse.put("status", "success");
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();

            data.put("id", products.getId());
            data.put("name", products.getName());
            data.put("price", products.getPrice().toString());
            data.put("status", products.getStatus().toString());
            data.put("description", products.getDescription());
            data.put("category", products.getCategory());
            data.put("userId", products.getUserId().getId());
            data.put("imageProducts", products.getImageProducts());

            jsonResponse.put("data", data);
        }


        return jsonResponse;
    }
}
