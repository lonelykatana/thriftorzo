package com.binar.kelompok3.secondhand.model.response.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponsePage {

    private int totalPage;
    private long totalElement;
    private int currentPage;
    private boolean isFirst;
    private boolean isLast;
    private int size;
    private List<ProductResponse> productResponses;

    public ProductResponsePage(int totalPage, long totalElement, int currentPage, boolean isFirst,
                               boolean isLast, int size, List<ProductResponse> productResponses) {
        this.totalPage = totalPage;
        this.totalElement = totalElement;
        this.currentPage = currentPage;
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.size = size;
        this.productResponses = productResponses;
    }

}
