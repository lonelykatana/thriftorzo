package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IProductsServiceTest {

    @Mock
    private ProductsRepository repository;

    @InjectMocks
    private ProductsServiceImpl service;

    @Test
    void getAllProductsPaginated() {

        Pageable pageable = PageRequest.of(0, 10);

        List<Products> productsList = new ArrayList<>();
        Page<Products> productsPage = new PageImpl<>(productsList);
        when(repository.findAllByOrderByCreatedOnDesc(pageable)).thenReturn(productsPage);

        Page<Products> mocked = service.getAllProductsPaginated(pageable);

        assertSame(productsPage, mocked);
    }

    @Test
    void getAllSoldProductsPaginated() {
        Pageable pageable = PageRequest.of(0, 10);
        Integer userId = 1;

        List<Products> productsList = new ArrayList<>();
        Page<Products> productsPage = new PageImpl<>(productsList);
        when(repository.getAllProductSoldPaginated(userId, pageable)).thenReturn(productsPage);

        Page<Products> mocked = service.getAllSoldProductsPaginated(userId, pageable);

        assertSame(productsPage, mocked);
    }

    @Test
    void findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 10);
        String name = "name";
        String category = "category";

        List<Products> productsList = new ArrayList<>();
        Page<Products> productsPage = new PageImpl<>(productsList);
        when(repository.findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndStatusAndPublish(name, category, 1, 1, pageable)).thenReturn(productsPage);

        Page<Products> mocked = service.findProductsByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category, pageable);

        assertSame(productsPage, mocked);
    }

    @Test
    void findProductsById() {
        String productId = "id";

        Products product = new Products();
        product.setId("id");
        product.setName("name");
        product.setPrice(1000d);

        when(repository.findProductsById(productId)).thenReturn(product);

        assertSame(product, service.findProductsById(productId));

    }


}
