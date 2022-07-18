package com.binar.kelompok3.secondhand.model.response.history;

import com.binar.kelompok3.secondhand.model.response.offers.TransactionResponse;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class HistoryResponsePaged {

    private int totalPage;
    private long totalElement;
    private int currentPage;
    private boolean isFirst;
    private boolean isLast;
    private int size;
    private List<TransactionResponse> historyResponse;

    public HistoryResponsePaged(Page<TransactionResponse> responses, int currentPage) {
        this.totalPage = responses.getTotalPages();
        this.totalElement = responses.getTotalElements();
        this.currentPage = currentPage;
        this.isFirst = responses.isFirst();
        this.isLast = responses.isLast();
        this.size = responses.getSize();
        this.historyResponse = responses.getContent();
    }
}
