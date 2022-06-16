package com.binar.kelompok3.secondhand.model;

import com.binar.kelompok3.secondhand.model.users.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "wishlist", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
public class Wishlist implements Serializable {

    private static final long serialVersionUID = 1865643897L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users userId;

}
