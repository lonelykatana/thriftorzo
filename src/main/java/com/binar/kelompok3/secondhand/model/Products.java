package com.binar.kelompok3.secondhand.model;

import com.binar.kelompok3.secondhand.model.users.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
public class Products implements Serializable {

    private static final long serialVersionUID = 1865643894L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Integer status;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_diminati")
    private boolean isDiminati;

    @Column(name = "image")
    private String image;

    //Entity Category belum dibuat
//    @ManyToOne(fetch = FetchType.EAGER)
//    @Column(name = "category_id")
//    private Category categoryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlistId;
}
