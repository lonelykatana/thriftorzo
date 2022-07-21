package com.binar.kelompok3.secondhand.service.products;

import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IProductsServiceTest {

    @Mock
    private ProductsRepository repository;

    @InjectMocks
    private IProductsService service = new ProductsServiceImpl();

    @Test
    void getAllProductsPaginated() {

        Pageable pageable = PageRequest.of(0, 10);

        List<Products> productsList = new ArrayList<>();
        Page<Products> productsPage = new PageImpl<>(productsList);
        Mockito.when(repository.findAllByOrderByCreatedOnDesc(pageable)).thenReturn(productsPage);

        Page<Products> mocked = service.getAllProductsPaginated(pageable);

        assertSame(productsPage, mocked);
    }

}