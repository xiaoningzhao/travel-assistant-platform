package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@Table(name="likes")
@IdClass(LikesId.class)
public class Likes {
    @Id
    private Long userId;

    @Id
    private Long postId;

}
