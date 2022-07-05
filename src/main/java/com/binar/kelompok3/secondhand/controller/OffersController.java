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

    @PostMapping("/add/{usedId}")
    public ResponseEntity<OfferRequest> addOffers(@PathVariable(value = "usedId") Integer userId,
                                                  @RequestBody OfferRequest offerRequest) {
        iOffersService.saveOffers(userId, offerRequest.getProductId(),
                offerRequest.getOfferPrice(), offerRequest.getStatus());
        return new ResponseEntity<>(offerRequest, HttpStatus.OK);
    }

    @GetMapping("/buyer-history/{userId}")
    public ResponseEntity<List<OfferResponseBuyer>> getBuyerHistory(@PathVariable Integer userId) {
        List<Offers> offers = iOffersService.getAllByUserId(userId);
        List<OfferResponseBuyer> offerResponses = offers.stream()
                .map(OfferResponseBuyer::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(offerResponses, HttpStatus.OK);
    }

    @GetMapping("/seller-history/{userId}")
    public ResponseEntity<List<OfferResponseSeller>> getHistorySeller(@PathVariable Integer userId) {
        List<Offers> offers = iOffersService.getHistorySeller(userId);
        List<OfferResponseSeller> offerResponses = offers.stream()
                .map(OfferResponseSeller::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(offerResponses, HttpStatus.OK);
    }

    @PutMapping("/update/{id}/{status}")
    public ResponseEntity<MessageResponse> updateOffers(@RequestParam Integer offerId,
                                                        @RequestParam Integer status) {
        iOffersService.updateOffers(offerId, status);
        return ResponseEntity.ok(new MessageResponse("Sukses mengupdate tawaran"));
    }

    @DeleteMapping("/delete/{offerId}")
    public ResponseEntity<MessageResponse> deleteOffers(@PathVariable Integer offerId) {
        iOffersService.deleteOffersById(offerId);
        return ResponseEntity.ok(
                new MessageResponse("Sukses menghapus tawaran" + iOffersService.findOffersById(offerId)));
    }

    @GetMapping("/get/{offerId}")
    public ResponseEntity<OfferResponseSeller> getOffer(@PathVariable Integer offerId) {
        Offers offers = iOffersService.findOffersById(offerId);
        OfferResponseSeller offerResponse = new OfferResponseSeller(offers);
        return new ResponseEntity<>(offerResponse, HttpStatus.OK);
    }

}
