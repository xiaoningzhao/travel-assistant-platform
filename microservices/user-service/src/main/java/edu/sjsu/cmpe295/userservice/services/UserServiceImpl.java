package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.exceptions.ConflictException;
import edu.sjsu.cmpe295.userservice.models.FavoritePlace;
import edu.sjsu.cmpe295.userservice.models.Friend;
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
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        if (userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        }
        return null;
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
    public List<Friend> getFriends(Long userId) {
        return friendRepository.findAllByUser1Id(userId);
    }

    @Override
    public List<FavoritePlace> getFavoritePlaces(Long userId) {
        return favoritePlaceRepository.findAllByUserId(userId);
    }
}
