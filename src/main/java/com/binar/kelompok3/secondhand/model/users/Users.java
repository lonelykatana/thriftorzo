package com.binar.kelompok3.secondhand.model.users;

import com.binar.kelompok3.secondhand.model.Cities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "email")
})
public class Users implements Serializable {

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

    @Column(name = "image")
    private String image;

    @Column(name = "cityName")
    private String cityName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
