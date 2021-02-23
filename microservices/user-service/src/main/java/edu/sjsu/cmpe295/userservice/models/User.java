package edu.sjsu.cmpe295.userservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Column(name="role")
    private String role;

    @Column(name="active")
    private Boolean active;

    @Column(name="token")
    private String token;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="creation_time")
    private LocalDateTime creationTime = LocalDateTime.now();
//
//    @JsonIgnoreProperties({"userFriend"})
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFriend")
//    private List<Friend> friends;
//
//    @JsonIgnoreProperties({"user"})
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    private List<FavoritePlace> favoritePlaces;

}
