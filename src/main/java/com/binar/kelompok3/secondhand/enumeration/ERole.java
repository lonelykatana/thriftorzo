package com.binar.kelompok3.secondhand.enumeration;

public enum ERole {
    BUYER(1), SELLER(2);

    private Integer number;

    ERole(Integer num) {
        this.number = num;
    }

    public Integer getNumber() {
        return number;
    }
}
