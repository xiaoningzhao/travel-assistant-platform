package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.models.FavoritePlace;
import edu.sjsu.cmpe295.userservice.models.Friend;
import edu.sjsu.cmpe295.userservice.models.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Long id);
    User register(User user);
    List<Friend> getFriends(Long userId);
    List<FavoritePlace> getFavoritePlaces(Long userId);
}
