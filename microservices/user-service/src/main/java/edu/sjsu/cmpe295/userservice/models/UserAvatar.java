package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Entity
@Table(name="user_avatar")
public class UserAvatar {

    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name="avatar_url")
    @Length(max = 500)
    private String avatarUrl;

}
