package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.request.transaction.OfferRequest;
import com.binar.kelompok3.secondhand.model.request.transaction.UpdateOfferRequest;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.offers.TransactionResponse;
import com.binar.kelompok3.secondhand.service.offers.IOffersService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

    private IOffersService iOffersService;
    private IUsersService iUsersService;

    /*@PostMapping("/buy-transaction/{userId}")
    public ResponseEntity<OfferRequest> addOffers(@PathVariable(value = "userId") Integer userId,
                                                  @RequestBody OfferRequest offerRequest) {
        iOffersService.saveOffers(userId, offerRequest.getProductId(),
                offerRequest.getOfferPrice(), offerRequest.getStatus());
        return new ResponseEntity<>(offerRequest, HttpStatus.OK);
    }*/

    @PostMapping("/buy-transaction")
    public ResponseEntity<OfferRequest> addOffersAuth(Authentication authentication,
                                                      @RequestBody OfferRequest offerRequest) {
        Users user = iUsersService.findByEmail(authentication.getName());
        iOffersService.saveOffers(user.getId(), offerRequest.getProductId(),
                offerRequest.getOfferPrice(), offerRequest.getStatus());
        return new ResponseEntity<>(offerRequest, HttpStatus.OK);
    }

    @PutMapping("/update-transaction")
    public ResponseEntity<MessageResponse> updateOffers(@RequestBody UpdateOfferRequest request) {
        iOffersService.updateOffers(request.getOfferId(), request.getStatus());
        return ResponseEntity.ok(new MessageResponse("Sukses mengupdate tawaran"));
    }

    @DeleteMapping("/delete-transaction")
    public ResponseEntity<MessageResponse> deleteOffers(@RequestParam Integer offerId) {
        iOffersService.deleteOffersById(offerId);
        return ResponseEntity.ok(
                new MessageResponse("Sukses menghapus tawaran" + iOffersService.findOffersById(offerId)));
    }

    @GetMapping("/get-transaction")
    public ResponseEntity<TransactionResponse> getOffer(@RequestParam Integer offerId) {
        Offers offers = iOffersService.findOffersById(offerId);
        TransactionResponse transactionResponse = new TransactionResponse(offers);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

}
