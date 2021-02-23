package edu.sjsu.cmpe295.userservice.controllers;

import edu.sjsu.cmpe295.userservice.models.FavoritePlace;
import edu.sjsu.cmpe295.userservice.models.Friend;
import edu.sjsu.cmpe295.userservice.models.User;
import edu.sjsu.cmpe295.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public User getUserProfile(@RequestParam(value = "email") @Email @NotNull @Length(max = 128) String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/registration")
    public User register(@RequestParam(value = "email") @Email @NotNull @Length(max = 128) String email,
                                @RequestParam(value = "password") @NotNull @Length(max = 32) String password,
                                @RequestParam(value = "firstName") @NotNull @Length(max = 50) String firstName,
                                @RequestParam(value = "lastName") @NotNull @Length(max = 50) String lastName,
                                @RequestParam(value = "address") @Length(max = 500) String address,
                                @RequestParam(value = "phone") @Length(max = 50) String phone) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhone(phone);

        userService.register(user);

        return user;
    }

    @GetMapping("/friends")
    public List<Friend> getFriend(@RequestParam(value = "userId") Long userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("/places")
    public List<FavoritePlace> getFavoritePlaces(@RequestParam(value = "userId") Long userId) {
        return userService.getFavoritePlaces(userId);
    }

}
