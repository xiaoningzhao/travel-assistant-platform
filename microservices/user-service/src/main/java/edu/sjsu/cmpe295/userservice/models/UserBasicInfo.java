package edu.sjsu.cmpe295.userservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserBasicInfo {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

}
