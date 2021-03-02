package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.models.FavoritePlace;
import edu.sjsu.cmpe295.userservice.models.Friend;
import edu.sjsu.cmpe295.userservice.models.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Long id);
    User register(User user);
    User modifyUserProfile(User user);
    List<String> getFriends(Long userId);
    Friend addNewFriend(Long user1Id, Long user2Id);
    Friend deleteFriend(Long user1Id, Long user2Id);
    List<String> getFavoritePlaces(Long userId);
    FavoritePlace addFavoritePlace(Long userId, String placeId);
    FavoritePlace deleteFavoritePlace(Long userId, String placeId);
}
