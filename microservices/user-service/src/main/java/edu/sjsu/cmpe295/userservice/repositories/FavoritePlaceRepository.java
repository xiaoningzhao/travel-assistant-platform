package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.FavoritePlace;
import edu.sjsu.cmpe295.userservice.models.FavoritePlaceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritePlaceRepository extends JpaRepository<FavoritePlace, FavoritePlaceId> {
    List<FavoritePlace> findAllByUserId (Long userId);
}
