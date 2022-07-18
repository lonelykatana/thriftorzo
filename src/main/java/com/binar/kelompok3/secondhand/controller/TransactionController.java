package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.request.transaction.OfferRequest;
import com.binar.kelompok3.secondhand.model.request.transaction.UpdateOfferRequest;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.notif.StatusTransactionResponse;
import com.binar.kelompok3.secondhand.model.response.history.TransactionResponse;
import com.binar.kelompok3.secondhand.service.offers.IOffersService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.binar.kelompok3.secondhand.utils.Constant.TRANSACTION_UPDATED;

@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

    private IOffersService iOffersService;
    private IProductsService productsService;
    private IUsersService iUsersService;

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
        return ResponseEntity.ok(new MessageResponse(TRANSACTION_UPDATED));
    }

    @DeleteMapping("/delete-transaction")
    public ResponseEntity<MessageResponse> deleteOffers(@RequestParam Integer offerId) {
        iOffersService.deleteOffersById(offerId);
        return ResponseEntity.ok(new MessageResponse("Successfully Deleted: " + iOffersService.findOffersById(offerId)));
    }

    @GetMapping("/get-transaction")
    public ResponseEntity<TransactionResponse> getOffer(@RequestParam Integer offerId) {
        Offers offers = iOffersService.findOffersById(offerId);
        TransactionResponse transactionResponse = new TransactionResponse(offers);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @GetMapping("/get-status-transaction")
    public ResponseEntity<StatusTransactionResponse> statusTransaction(Authentication authentication,
                                                                       @RequestParam(value = "productId") String productId) {
        Users user = iUsersService.findByEmail(authentication.getName());
        Boolean status = iOffersService.getTransaction(user.getId(), productId);

        StatusTransactionResponse response = new StatusTransactionResponse(user, productId);
        response.setStatus(status.equals(true));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
