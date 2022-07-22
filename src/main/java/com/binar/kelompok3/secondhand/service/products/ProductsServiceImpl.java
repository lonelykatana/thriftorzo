package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.enumeration.ERole;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponsePage;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import com.binar.kelompok3.secondhand.service.notification.INotificationService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.binar.kelompok3.secondhand.utils.Constant.*;


@Service
@AllArgsConstructor
public class ProductsServiceImpl implements IProductsService {

    private ProductsRepository productsRepository;
    private IUsersService iUsersService;
    private INotificationService iNotificationService;

    @Override
    public void saveProducts(String id, String name, Double price, Integer status, Integer publish,
                             String description,
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

        Products products1 = findProductsById(id);
        if (publish.equals(1)) {
            iNotificationService.saveNotification(BERHASIL_DITERBITKAN, INFO_DITERBITKAN,
                    products1, ERole.BUYER.getNumber(), userId);
        }
    }

    @Override
    @CachePut
    public void updateProducts(String name, Double price, Integer status, Integer publish,
                               String description,
                               String category, String id) {
        Products products1 = findProductsById(id);
        if (products1.getPublish().equals(0)){
            iNotificationService.saveNotification(BERHASIL_DITERBITKAN, INFO_DITERBITKAN,
                    products1, ERole.BUYER.getNumber());
        }
        productsRepository.updateProducts(name, price, status, publish, description, category, id);

    }

    @Override
    @Cacheable
    public Page<Products> getAllProductsPaginated(Pageable pageable) {
        return productsRepository.findAllByOrderByCreatedOnDesc(pageable);
    }

    @Override
    @Cacheable
    public Page<Products> getAllSoldProductsPaginated(Integer userId, Pageable pageable) {
        return productsRepository.getAllProductSoldPaginated(userId, pageable);
    }

    @Override
    @Cacheable
    public Page<Products> getProductsByUserId(Integer userId, Pageable pageable) {
        List<Products> usersList = productsRepository.getProductsByUserId(userId);
        return new PageImpl<>(usersList);
    }

    @Override
    @Cacheable
    public Page<Products> findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category, Pageable pageable) {
        return productsRepository.findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndStatusAndPublish(name, category, 1, 1, pageable);
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
    public void updateStatus(String id, Integer status) {
        Products products = findProductsById(id);
        products.setStatus(status);
        productsRepository.save(products);
    }

    @Override
    @Cacheable
    public ResponseEntity<MessageResponse> getMessageResponse(Integer page,
                                                              Integer size,
                                                              Page<Products> products) {
        List<ProductResponse> productResponses = products.stream()
                .map(product -> new ProductResponse(product, product.getUserId()))
                .collect(Collectors.toList());
        if (products.hasContent()) {
            ProductResponsePage productResponsePage = new ProductResponsePage(products.getTotalPages(),
                    products.getTotalElements(), page, products.isFirst(), products.isLast(),
                    size, productResponses);
            return new ResponseEntity(productResponsePage, HttpStatus.OK);
        } else {
            return new ResponseEntity(new MessageResponse(DATA_EMPTY), HttpStatus.NO_CONTENT);
        }
    }
}
