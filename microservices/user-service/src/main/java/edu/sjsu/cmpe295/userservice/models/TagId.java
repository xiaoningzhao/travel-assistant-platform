package edu.sjsu.cmpe295.userservice.models;

import javax.persistence.Column;
import java.io.Serializable;

public class TagId implements Serializable {
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "tag")
    private String tag;
}
