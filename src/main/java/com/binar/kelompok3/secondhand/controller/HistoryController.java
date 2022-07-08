package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.response.offers.TransactionResponse;
import com.binar.kelompok3.secondhand.service.offers.IOffersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HistoryController {

    private IOffersService iOffersService;

    @GetMapping("/buyer-history/{userId}")
    public ResponseEntity<List<TransactionResponse>> getBuyerHistory(@PathVariable Integer userId) {
        List<Offers> offers = iOffersService.getAllByUserId(userId);
        List<TransactionResponse> offerResponses = offers.stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(offerResponses, HttpStatus.OK);
    }

    @GetMapping("/seller-history/{userId}")
    public ResponseEntity<List<TransactionResponse>> getHistorySeller(@PathVariable Integer userId) {
        List<Offers> offers = iOffersService.getHistorySeller(userId);
        List<TransactionResponse> offerResponses = offers.stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(offerResponses, HttpStatus.OK);
    }
}
