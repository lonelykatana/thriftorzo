package com.binar.kelompok3.secondhand.model.entity;

import com.binar.kelompok3.secondhand.model.DateModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notification")
public class Notification extends DateModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String info;

    private Integer roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = true)
    private Offers offerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private Products productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private Users userId;

    private Boolean isRead = false;
}
