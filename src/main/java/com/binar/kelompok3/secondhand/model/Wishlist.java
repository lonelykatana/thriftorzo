package com.binar.kelompok3.secondhand.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wishlist", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
public class Wishlist implements Serializable {

    private static final long serialVersionUID = 1865643897L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id", nullable = false)
    private Integer id;

    private Integer userId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Products products;

    public Wishlist(Integer userId, Integer productId) {
        this.userId = userId;
        this.productId = productId;
        this.createdDate = new Date();
    }

}
