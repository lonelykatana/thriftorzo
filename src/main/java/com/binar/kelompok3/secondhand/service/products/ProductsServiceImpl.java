package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.DateModel;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponse;
import com.binar.kelompok3.secondhand.model.response.product.ProductResponsePage;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import com.binar.kelompok3.secondhand.service.notification.INotificationService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
            iNotificationService.saveNotification("Berhasil diterbitkan", products1, userId);
        }
    }


    @Override
    public void updateProducts(String name, Double price, Integer status, Integer publish,
                               String description,
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
    public Page<Products> getAllSoldProductsPaginated(Integer userId, Pageable pageable) {
        return productsRepository.getAllProductSoldPaginated(userId, pageable);
    }

    @Override
    public Page<Products> getAllProductsDiminati(Integer userId, Pageable pageable) {
        return productsRepository.getProductsDiminati(userId, pageable);
    }

    @Override
    public Page<Products> getProductsByUserId(Integer userId, Pageable pageable) {
        List<Products> usersList = productsRepository.getProductsByUserId(userId);
        return new PageImpl<>(usersList);
    }

    // ini
    @Override
    public Page<Products> searchProductByNamePaginated(String productName, Pageable pageable) {
        List<Products> products = productsRepository.findProductsByNameContainingIgnoreCase(productName, pageable);
        return new PageImpl<>(filterPublished(products));
    }

    @Override
    public Page<Products> findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category, Pageable pageable) {
        return productsRepository.findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndStatusAndPublish(name, category, 1, 1, pageable);
    }

    // ini
    @Override
    public Page<Products> filterProductByCategoryPaginated(String category, Pageable pageable) {
        List<Products> products = productsRepository.findProductsByCategoryContainingIgnoreCase(category, pageable);

        return new PageImpl<>(filterPublished(products));
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
    public ResponseEntity<MessageResponse> getMessageResponse(Integer page, Integer size,
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
            return new ResponseEntity(new MessageResponse("Data Kosong!"), HttpStatus.NO_CONTENT);
        }
    }

    private List<Products> filterPublished(List<Products> products) {
        return products.stream()
                .filter(val -> val.getPublish().equals(1))
                .sorted(Comparator.comparing(DateModel::getCreatedOn).reversed())
                .collect(Collectors.toList());
    }

}
