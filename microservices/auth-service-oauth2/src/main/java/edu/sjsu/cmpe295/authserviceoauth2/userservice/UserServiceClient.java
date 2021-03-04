package edu.sjsu.cmpe295.authserviceoauth2.userservice;

import edu.sjsu.cmpe295.authserviceoauth2.models.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/user/profile/{email}")
    User getUser(@PathVariable @NotNull @Length(max = 128) String email);
}
