package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="friend")
@IdClass(FriendId.class)
public class Friend {
    @Id
    private Long user1Id;

    @Id
    private Long user2Id;

    @Column(name = "status")
    private String status;
}
