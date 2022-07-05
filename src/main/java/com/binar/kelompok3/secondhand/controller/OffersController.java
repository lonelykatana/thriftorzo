package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.request.OfferRequest;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.offers.OfferResponseBuyer;
import com.binar.kelompok3.secondhand.model.response.offers.OfferResponseSeller;
import com.binar.kelompok3.secondhand.service.offers.IOffersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/offer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OffersController {

    private IOffersService iOffersService;


    @PostMapping("/add")
    public ResponseEntity<OfferRequest> addOffers(@RequestBody OfferRequest offerRequest) {
        iOffersService.saveOffers(offerRequest.getUserId(), offerRequest.getProductId(),
                offerRequest.getOfferPrice(), offerRequest.getStatus());
        return new ResponseEntity<>(offerRequest, HttpStatus.OK);
    }

    @GetMapping("/buyer-history/{userId}")
    public ResponseEntity<List<OfferResponseBuyer>> getBuyerHistory(@PathVariable Integer userId) {
        List<Offers> offers = iOffersService.getAllByUserId(userId);
        List<OfferResponseBuyer> offerResponses =
                offers.stream().map(offers1 -> new OfferResponseBuyer(offers1)).collect(
                        Collectors.toList());

        return new ResponseEntity<>(offerResponses, HttpStatus.OK);
    }

    @GetMapping("/seller-history/{userId}")
    public ResponseEntity<List<OfferResponseSeller>> getHistorySeller(@PathVariable Integer userId) {
        List<Offers> offers = iOffersService.getHistorySeller(userId);
        List<OfferResponseSeller> offerResponses =
                offers.stream().map(offers1 -> new OfferResponseSeller(offers1)).collect(
                        Collectors.toList());

        return new ResponseEntity<>(offerResponses, HttpStatus.OK);
    }

    @PutMapping("/update/{id}/{status}")
    public ResponseEntity<OfferResponseBuyer> updateOffers(@PathVariable Integer id,
                                                           @PathVariable Integer status) {
        iOffersService.updateOffers(id, status);
        Offers offers = iOffersService.getOffersById(id);
        OfferResponseBuyer offerResponse = new OfferResponseBuyer(offers);
        return new ResponseEntity<>(offerResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteOffers(@PathVariable Integer id) {
        iOffersService.deleteOffersById(id);
        return ResponseEntity.ok(
                new MessageResponse("Sukses menghapus tawaran" + iOffersService.getOffersById(id)));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OfferResponseBuyer> getOffer(@PathVariable Integer id) {
        Offers offers = iOffersService.getOffersById(id);
        OfferResponseBuyer offerResponse = new OfferResponseBuyer(offers);
        return new ResponseEntity<>(offerResponse, HttpStatus.OK);
    }

}
