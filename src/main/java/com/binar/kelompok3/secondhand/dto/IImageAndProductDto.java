package com.binar.kelompok3.secondhand.dto;

import java.util.Date;

public interface IImageAndProductDto {
    String getId();

    String getName();

    Double getPrice();

    Integer getStatus();

    String getDescription();

    String getCategory();

    String getUserId();

    Date getCreatedOn();

    Date getUpdatedOn();

    String getUrl();


}
