package edu.sjsu.cmpe295.userservice.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="post_image")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="post_id")
    private Long postId;

    @NotNull
    @Column(name="image_url")
    @Length(max = 500)
    private String imageUrl;

}
