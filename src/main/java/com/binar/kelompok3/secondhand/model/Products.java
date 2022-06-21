package com.binar.kelompok3.secondhand.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "category")
    private String category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users userId;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

}
