package edu.sjsu.cmpe295.authserviceoauth2.userservice;

import edu.sjsu.cmpe295.authserviceoauth2.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/user/profile")
    User getUser(@RequestParam("email") String email);
}
