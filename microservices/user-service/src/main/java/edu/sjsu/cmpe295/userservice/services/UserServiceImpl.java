package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.exceptions.ConflictException;
import edu.sjsu.cmpe295.userservice.exceptions.NotFoundException;
import edu.sjsu.cmpe295.userservice.models.FavoritePlace;
import edu.sjsu.cmpe295.userservice.models.Friend;
import edu.sjsu.cmpe295.userservice.models.FriendId;
import edu.sjsu.cmpe295.userservice.models.User;
import edu.sjsu.cmpe295.userservice.repositories.FavoritePlaceRepository;
import edu.sjsu.cmpe295.userservice.repositories.FriendRepository;
import edu.sjsu.cmpe295.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FavoritePlaceRepository favoritePlaceRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUserByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return userRepository.findByEmail(email);
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public User getUserById(Long id) {
        if (userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public User register(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setActive(false);
            user.setRole("ROLE_USER");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            String token = UUID.randomUUID().toString().replace("-", "");
            user.setToken(token);
            userRepository.save(user);
            return user;
        }else{
            throw new ConflictException("User email has been used, please try another one.");
        }
    }

    @Override
    public User modifyUserProfile(User user) {
        if (userRepository.existsById(user.getId())) {
            User modifiedUser = userRepository.findById(user.getId()).get();
            if(!modifiedUser.getEmail().equals(user.getEmail())){
                if(userRepository.existsByEmail(user.getEmail())){
                    throw new ConflictException("User email has been used, please try another one.");
                }else{
                    modifiedUser.setEmail(user.getEmail());
                }
            }
            modifiedUser.setFirstName(user.getFirstName());
            modifiedUser.setLastName(user.getLastName());
            modifiedUser.setPhone(user.getPhone());
            modifiedUser.setAddress(user.getAddress());
            return userRepository.save(modifiedUser);
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public List<String> getFriends(Long userId) {
        if(userRepository.existsById(userId)) {
            return friendRepository.findFriendsListByUser1Id(userId);
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public Friend addNewFriend(Long user1Id, Long user2Id) {
        if(userRepository.existsById(user1Id) && userRepository.existsById(user2Id)){
            FriendId friendId = new FriendId();
            friendId.setUser1Id(user1Id);
            friendId.setUser2Id(user2Id);
            if(friendRepository.existsById(friendId)){
                throw new ConflictException("Friend relationship already exists.");
            }
            Friend friend = new Friend();
            friend.setUser1Id(user1Id);
            friend.setUser2Id(user2Id);
            friend.setStatus("1");
            return friendRepository.save(friend);
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public Friend deleteFriend(Long user1Id, Long user2Id) {
        if(userRepository.existsById(user1Id) && userRepository.existsById(user2Id)){
            FriendId friendId = new FriendId();
            friendId.setUser1Id(user1Id);
            friendId.setUser2Id(user2Id);
            if(friendRepository.existsById(friendId)){
                Friend friend = friendRepository.findById(friendId).get();
                friendRepository.delete(friend);
                return friend;
            }else{
                throw new NotFoundException("Friend relationship not exists.");
            }
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public List<String> getFavoritePlaces(Long userId) {
        if(userRepository.existsById(userId)) {
            return favoritePlaceRepository.findFavoritePlaceListByUserId(userId);
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public FavoritePlace addFavoritePlace(Long userId, String placeId) {
        if(userRepository.existsById(userId)){
            if(favoritePlaceRepository.findFavoritePlaceByUserIdAndPlaceId(userId, placeId) == null){
                FavoritePlace favoritePlace = new FavoritePlace();
                favoritePlace.setUserId(userId);
                favoritePlace.setPlaceId(placeId);
                return favoritePlaceRepository.save(favoritePlace);
            }else{
                throw new ConflictException("Favorite place already exists.");
            }
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public FavoritePlace deleteFavoritePlace(Long userId, String placeId) {
        if(userRepository.existsById(userId)){
            if(favoritePlaceRepository.findFavoritePlaceByUserIdAndPlaceId(userId, placeId) != null){
                FavoritePlace favoritePlace = favoritePlaceRepository.findFavoritePlaceByUserIdAndPlaceId(userId, placeId);
                favoritePlaceRepository.delete(favoritePlace);
                return favoritePlace;
            }else{
                throw new ConflictException("Favorite place not exists.");
            }
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }
}
