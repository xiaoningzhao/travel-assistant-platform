package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@Table(name="tag")
@IdClass(TagId.class)
public class Tag {
    @Id
    private Long postId;

    @Id
    private String tag;
}
