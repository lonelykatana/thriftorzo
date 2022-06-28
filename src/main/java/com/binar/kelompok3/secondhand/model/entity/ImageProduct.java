package com.binar.kelompok3.secondhand.model.entity;

import com.binar.kelompok3.secondhand.model.DateModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class ImageProduct extends DateModel implements Serializable {
    private static final long serialVersionUID = 1865643898L;

    @Id
    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Products products;
}
