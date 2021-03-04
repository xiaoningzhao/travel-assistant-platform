package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class FriendId implements Serializable {
    @Column(name = "user1_id")
    private Long user1Id;

    @Column(name = "user2_id")
    private Long user2Id;
}
