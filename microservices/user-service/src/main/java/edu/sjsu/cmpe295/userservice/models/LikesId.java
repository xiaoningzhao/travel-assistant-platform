package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class LikesId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;
}
