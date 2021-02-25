package edu.sjsu.cmpe295.authserviceoauth2.security;

import edu.sjsu.cmpe295.authserviceoauth2.models.User;
import edu.sjsu.cmpe295.authserviceoauth2.userservice.UserServiceClient;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserServiceClient userServiceClient;

    public UserDetailsServiceImp(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceClient.getUser(username);

        if(user!=null){
            return new AuthUser(user);
        }else{
            throw new UsernameNotFoundException("User does not exist.");
        }
    }
}
