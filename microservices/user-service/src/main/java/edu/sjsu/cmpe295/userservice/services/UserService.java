package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.models.FavoritePlace;
import edu.sjsu.cmpe295.userservice.models.Friend;
import edu.sjsu.cmpe295.userservice.models.User;
import edu.sjsu.cmpe295.userservice.models.UserBasicInfo;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Long id);
    UserBasicInfo getUserBasicProfile(Long userId);
    User register(User user);
    User modifyUserProfile(User user);
    List<UserBasicInfo> getFriends(Long userId);
    Friend addNewFriend(Long user1Id, String user2email);
    Friend deleteFriend(Long user1Id, String user2email);
    List<String> getFavoritePlaces(Long userId);
    FavoritePlace addFavoritePlace(Long userId, String placeId);
    FavoritePlace deleteFavoritePlace(Long userId, String placeId);
}
