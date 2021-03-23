package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.exceptions.ConflictException;
import edu.sjsu.cmpe295.userservice.exceptions.FileException;
import edu.sjsu.cmpe295.userservice.exceptions.NotFoundException;
import edu.sjsu.cmpe295.userservice.models.*;
import edu.sjsu.cmpe295.userservice.repositories.FavoritePlaceRepository;
import edu.sjsu.cmpe295.userservice.repositories.FriendRepository;
import edu.sjsu.cmpe295.userservice.repositories.UserAvatarRepository;
import edu.sjsu.cmpe295.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FavoritePlaceRepository favoritePlaceRepository;
    private final UserAvatarRepository userAvatarRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${image.upload.path}")
    private String imageUploadPath;

    @Value("${image.static.path}")
    private String staticPath;

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
    public UserBasicInfo getUserBasicProfile(Long userId) {
        if (userRepository.findById(userId).isPresent()){
            User user = userRepository.findById(userId).get();
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.setId(user.getId());
            userBasicInfo.setEmail(user.getEmail());
            userBasicInfo.setFirstName(user.getFirstName());
            userBasicInfo.setLastName(user.getLastName());

            if(userAvatarRepository.findById(userId).isPresent()){
                userBasicInfo.setAvatarUrl(userAvatarRepository.findById(userId).get().getAvatarUrl());
            }else{
                userBasicInfo.setAvatarUrl("");
            }

            return userBasicInfo;
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public User register(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setActive(true);
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
    public List<UserBasicInfo> getFriends(Long userId) {
        if(userRepository.existsById(userId)) {
            List<Long> friendIds = friendRepository.findFriendsListByUser1Id(userId);
            List<UserBasicInfo> friends = new ArrayList<>();
            for(Long friendId : friendIds){
                UserBasicInfo userBasicInfo = new UserBasicInfo();
                User user = userRepository.findById(friendId).get();
                userBasicInfo.setId(friendId);
                userBasicInfo.setEmail(user.getEmail());
                userBasicInfo.setFirstName(user.getFirstName());
                userBasicInfo.setLastName(user.getLastName());
                if(userAvatarRepository.findById(friendId).isPresent()){
                    userBasicInfo.setAvatarUrl(userAvatarRepository.findById(friendId).get().getAvatarUrl());
                }else{
                    userBasicInfo.setAvatarUrl("");
                }
                friends.add(userBasicInfo);
            }
            return friends;
        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public Friend addNewFriend(Long user1Id, String user2email) {
        if(userRepository.existsById(user1Id) && userRepository.existsByEmail(user2email)){
            Long user2Id = userRepository.findByEmail(user2email).getId();
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
    public Friend deleteFriend(Long user1Id, String user2email) {
        if(userRepository.existsById(user1Id) && userRepository.existsByEmail(user2email)){
            Long user2Id = userRepository.findByEmail(user2email).getId();
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

    @Override
    public UserAvatar getUserAvatar(Long userId) {
        if(userRepository.existsById(userId)){
            if(userAvatarRepository.findById(userId).isPresent()) {
                return userAvatarRepository.findById(userId).get();
            }else {
                throw new NotFoundException("User does not have avatar");
            }

        }else{
            throw new NotFoundException("User does not exist.");
        }
    }

    @Override
    public UserAvatar addUserAvatar(Long userId, String avatarUrl) {
        if(userAvatarRepository.findById(userId).isPresent()){
            UserAvatar userAvatar = userAvatarRepository.findById(userId).get();
            userAvatar.setAvatarUrl(avatarUrl);
            return userAvatarRepository.save(userAvatar);
        }else{
            UserAvatar userAvatar = new UserAvatar();
            userAvatar.setUserId(userId);
            userAvatar.setAvatarUrl(avatarUrl);
            return userAvatarRepository.save(userAvatar);
        }
    }

    @Override
    public String uploadUserAvatar(MultipartFile multipartFile, Long userId) {
        String fileName = multipartFile.getOriginalFilename();
        String suffixName = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String filePath = imageUploadPath +"/"+userId+"/avatar";

        File dir = new File(filePath);
        if(dir.exists()){
            String[] content = dir.list();
            if (content != null) {
                for(String name : content){
                    File temp = new File(filePath, name);
                    if(!temp.isDirectory()){
                        if(!temp.delete()){
                            System.err.println("Failed to delete " + name);
                        }
                    }
                }
            }
        }

        fileName = UUID.randomUUID() + suffixName;
//        fileName = "avatar" + suffixName;
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            multipartFile.transferTo(file);
            //return file path
            return staticPath + userId + "/avatar/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("File cannot save");
        }
    }
}
