package com.binar.kelompok3.secondhand.service.offers;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.OffersRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OffersServiceImplTest {

    @Mock
    private OffersRepository repository;

    @InjectMocks
    private IOffersService service = new OffersServiceImpl();

    @Test
    void getAllOffers() {

        List<Offers> offersList = new ArrayList<>();
        when(repository.getAllOffers()).thenReturn(offersList);

        assertSame(offersList, service.getAllOffers());
    }

    @Test
    void getAllByUserId() {
        Integer userId = 1;

        List<Offers> offersList = new ArrayList<>();
        when(repository.findAllByUserId(userId)).thenReturn(offersList);

        assertSame(offersList, service.getAllByUserId(userId));
    }

    @Test
    void getHistorySeller() {
        Integer userId = 1;

        List<Offers> offersList = new ArrayList<>();
        when(repository.getHistorySeller(userId)).thenReturn(offersList);

        assertSame(offersList, service.getHistorySeller(userId));
    }

    @Test
    void findOffersById() {
        Integer id = 1;

        Offers offers = new Offers();
        offers.setId(1);
        offers.setOfferPrice(1000d);
        offers.setUserId(new Users("name", "email@email.com", "password"));

        when(repository.findOffersById(1)).thenReturn(offers);

        assertSame(offers, service.findOffersById(1));
    }

    @Test
    void getTransaction() {
        Integer userId = 1;
        String productId = "productId1";

        Offers offers = new Offers();
        offers.setId(1);
        offers.setOfferPrice(1000d);
        offers.setUserId(new Users("name", "email@email.com", "password"));

        when(repository.getTransaction(userId, productId)).thenReturn(offers);

        assertEquals(true, service.getTransaction(1, productId));
        assertEquals(false, service.getTransaction(2, productId));
    }
}