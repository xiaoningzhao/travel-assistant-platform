package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.FavoritePlace;
import edu.sjsu.cmpe295.userservice.models.FavoritePlaceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritePlaceRepository extends JpaRepository<FavoritePlace, FavoritePlaceId> {
    List<FavoritePlace> findAllByUserId (Long userId);

    FavoritePlace findFavoritePlaceByUserIdAndPlaceId(Long userId, String placeId);

    @Query(value = "select placeId from FavoritePlace where userId=:userId")
    List<String> findFavoritePlaceListByUserId(@Param("userId") Long userId);
}
