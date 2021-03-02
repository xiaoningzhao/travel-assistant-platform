package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="favorite_place")
@IdClass(FavoritePlaceId.class)
public class FavoritePlace {
    @Id
    private Long userId;

    @Id
    private String placeId;

}
