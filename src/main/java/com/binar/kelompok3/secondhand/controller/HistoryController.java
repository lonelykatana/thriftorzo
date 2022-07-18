package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.response.history.HistoryPageResponse;
import com.binar.kelompok3.secondhand.model.response.history.TransactionResponse;
import com.binar.kelompok3.secondhand.service.offers.IOffersService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HistoryController {

    private IOffersService iOffersService;
    private IUsersService iUsersService;

    @GetMapping("/buyer-history")
    public ResponseEntity<HistoryPageResponse> getBuyerHistoryAuth(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                   @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
                                                                   Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());
        List<Offers> offers = iOffersService.getAllByUserId(user.getId());
        List<TransactionResponse> historyResponse = offers.stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());

        if (offers.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        HistoryPageResponse notificationPageResponse = convertToPaginated(historyResponse, page, size);
        return new ResponseEntity<>(notificationPageResponse, HttpStatus.OK);
    }

    @GetMapping("/seller-history")
    public ResponseEntity<HistoryPageResponse> getHistorySellerAuth(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                    @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
                                                                    Authentication authentication) {
        Users user = iUsersService.findByEmail(authentication.getName());
        List<Offers> offers = iOffersService.getHistorySeller(user.getId());
        List<TransactionResponse> historyResponse = offers.stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());

        if (offers.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        HistoryPageResponse notificationPageResponse = convertToPaginated(historyResponse, page, size);
        return new ResponseEntity<>(notificationPageResponse, HttpStatus.OK);
    }

    private HistoryPageResponse convertToPaginated(List<TransactionResponse> historyResponse, int page, int size) {
        int responseSize = historyResponse.size();
        Pageable paging = PageRequest.of(page, size);
        int start = Math.min((int) paging.getOffset(), responseSize);
        int end = Math.min((start + paging.getPageSize()), responseSize);
        Page<TransactionResponse> paged = new PageImpl<>(historyResponse.subList(start, end), paging, responseSize);
        return new HistoryPageResponse(paged, page);
    }
}
