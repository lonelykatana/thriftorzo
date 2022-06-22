package com.binar.kelompok3.secondhand.model.entity;

import com.binar.kelompok3.secondhand.model.DateModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "email")
})
public class Users extends DateModel implements Serializable {

    private static final long serialVersionUID = 1865643891L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "alamat", length = 300)
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "cityName")
    private String cityName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(
            mappedBy = "users",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private final List<Images> imagesList = new ArrayList<>();

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void addImage(Images images) {
        imagesList.add(images);
        images.setUsers(this);
    }

    public void deleteImage(Images images) {
        imagesList.remove(images);
        images.setUsers(null);
    }
}
