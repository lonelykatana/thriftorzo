package com.binar.kelompok3.secondhand.model.entity;

import com.binar.kelompok3.secondhand.model.DateModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
public class Products extends DateModel implements Serializable {

    private static final long serialVersionUID = 1865643894L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Integer status;

    @Column(name = "publish")
    private Integer publish;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category")
    private String category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users userId;

    @OneToMany(
            mappedBy = "products",
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    //@JsonIgnore
    @JsonManagedReference
    private List<ImageProduct> imageProducts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productId")
    private List<Wishlist> wishlists;

    public void add(ImageProduct imageProduct) {
        imageProducts.add(imageProduct);
        imageProduct.setProducts(this);
    }

    public void deleteImageProduct(ImageProduct imageProduct) {
        imageProducts.remove(imageProduct);
        imageProduct.setProducts(null);
    }

}
