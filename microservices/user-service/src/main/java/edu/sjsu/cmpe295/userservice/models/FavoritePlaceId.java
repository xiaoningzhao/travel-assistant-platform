package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class FavoritePlaceId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "place_id")
    private Long placeId;
}
