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

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user1_id", insertable = false, updatable = false)
//    private User userFriend;

}
