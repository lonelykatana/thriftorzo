package com.binar.kelompok3.secondhand.model;

import com.binar.kelompok3.secondhand.model.users.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "offers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
public class Offers implements Serializable {

    private static final long serialVersionUID = 1865643895L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Products productId;

    @Column(name = "offer_price")
    private Double offerPrice;

    @Column(name = "status")
    private Integer status;
}
