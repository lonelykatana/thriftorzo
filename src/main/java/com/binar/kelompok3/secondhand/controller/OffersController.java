package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.service.offers.IOffersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OffersController {

    private IOffersService iOffersService;

    public String addOffers(Integer userId, String productId, Double offerPrice, Integer status) {
        iOffersService.saveOffers(userId, productId, offerPrice, status);
        return "sukses add offer";
    }

    public List<Offers> getBuyerHistory(Integer userId) {
        return iOffersService.getAllByUserId(userId);
    }

    public List<Offers> getHistorySeller(Integer productId, Integer userId) {
        return iOffersService.getHistorySeller(productId, userId);
    }

    public String updateOffers(Integer id, Integer status) {
        iOffersService.updateOffers(id, status);
        return "sukses update offer : " + iOffersService.getOffersById(id);
    }

    public String deleteOffers(Integer id) {
        iOffersService.deleteOffersById(id);
        return "sukses menghapus offer : " + iOffersService.getOffersById(id);
    }

    public Offers getOffer(Integer id) {
        return iOffersService.getOffersById(id);
    }

}
