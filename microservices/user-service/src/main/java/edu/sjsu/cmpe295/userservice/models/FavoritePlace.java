package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="favorite_place")
@IdClass(FavoritePlaceId.class)
public class FavoritePlace {
    @Id
    private Long userId;

    @Id
    private String placeId;

    @Column(name="place_name")
    @Length(max = 200)
    private String placeName;

    @Column(name="place_address")
    @Length(max = 500)
    private String placeAddress;

    @Column(name="place_lat")
    private Double placeLat;

    @Column(name="place_lng")
    private Double placeLng;

    @Column(name="place_phone")
    @Length(max = 50)
    private String placePhone;

    @Column(name="place_rating")
    private Double placeRating;


}
