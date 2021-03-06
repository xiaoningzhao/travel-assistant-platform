package edu.sjsu.cmpe295.userservice.controllers;

import edu.sjsu.cmpe295.userservice.exceptions.ConflictException;
import edu.sjsu.cmpe295.userservice.models.*;
import edu.sjsu.cmpe295.userservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Tag(name = "User Service", description = "User service")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "Retrieve user information by email", description = "Retrieve user information by email", tags = { "User Service" })
    @GetMapping("/profile/{email}")
    public User getUserProfile(@Parameter(description = "User email")
                                   @PathVariable @NotNull @Length(max = 128) String email) {
        return userService.getUserByEmail(email);
    }

    @Operation(summary = "Retrieve user basic information by user ID", description = "Retrieve user basic information by user ID", tags = { "User Service" })
    @GetMapping("/profile/basic/{userId}")
    public UserBasicInfo getUserBasicProfile(@Parameter(description = "User ID")
                               @PathVariable @NotNull Long userId) {
        return userService.getUserBasicProfile(userId);
    }

    @Operation(summary = "User register", description = "Create a new user", tags = { "User Service" })
    @PostMapping("/registration")
    public User register(@Valid @RequestBody User user){
        userService.register(user);
        return user;
    }

    @Operation(summary = "Modify user profile", description = "Modify user profile", tags = { "User Service" })
    @PutMapping("/profile")
    public User modifyUserProfile(@Valid @RequestBody User user){
        userService.modifyUserProfile(user);
        return user;
    }

    @Operation(summary = "Retrieve user's friends' ID", description = "Retrieve user's friends' ID", tags = { "User Service" })
    @GetMapping("/friends/{userId}")
    public List<UserBasicInfo> getFriend(@PathVariable Long userId) {
        return userService.getFriends(userId);
    }

    @Operation(summary = "Add a friend", description = "Create a new friendship relation between user1 and user2", tags = { "User Service" })
    @PostMapping("/friends/{user1Id}/{user2email}")
    public Friend addNewFriend(@PathVariable Long user1Id, @PathVariable String user2email){
        return userService.addNewFriend(user1Id, user2email);
    }

    @Operation(summary = "Delete a friend", description = "Delete a friendship relation between user1 and user2", tags = { "User Service" })
    @DeleteMapping("/friends/{user1Id}/{user2email}")
    public Friend deleteFriend(@PathVariable Long user1Id, @PathVariable String user2email){
        return userService.deleteFriend(user1Id, user2email);
    }

    @Operation(summary = "Retrieve user's favorite places", description = "Retrieve user's favorite places", tags = { "User Service" })
    @GetMapping("/places/{userId}")
    public List<String> getFavoritePlaces(@PathVariable Long userId) {
        return userService.getFavoritePlaces(userId);
    }

    @Operation(summary = "Retrieve user's favorite places with detailed information", description = "Retrieve user's favorite places with detailed information", tags = { "User Service" })
    @GetMapping("/places/detail/{userId}")
    public List<FavoritePlace> getFavoritePlacesDetail(@PathVariable Long userId) {
        return userService.getFavoritePlacesDetail(userId);
    }

    @Operation(summary = "Add a favorite place", description = "Add a favorite place", tags = { "User Service" })
    @PostMapping("/places")
    public FavoritePlace addFavoritePlace(@Valid @RequestBody FavoritePlace favoritePlace){
        return userService.addFavoritePlace(favoritePlace);
    }

    @Operation(summary = "Delete a favorite place", description = "Delete a favorite place", tags = { "User Service" })
    @DeleteMapping("/places/{userId}/{placeId}")
    public FavoritePlace deleteFavoritePlace(@PathVariable Long userId, @PathVariable String placeId){
        return userService.deleteFavoritePlace(userId, placeId);
    }

    @Operation(summary = "Retrieve user's avatar url", description = "Retrieve user's avatar url", tags = { "User Service" })
    @GetMapping("/profile/avatar/{userId}")
    public UserAvatar getUserAvatar(@PathVariable Long userId) {
        return userService.getUserAvatar(userId);
    }

    @PostMapping("/profile/avatar/{userId}")
    public UserAvatar uploadUserAvatar(@RequestParam("image") MultipartFile multipartFile, @PathVariable Long userId){
        if (multipartFile.isEmpty()) {
            throw new ConflictException("Image file is empty");
        }
        String avatarUrl = userService.uploadUserAvatar(multipartFile, userId);
        return userService.addUserAvatar(userId, avatarUrl);
    }

}
