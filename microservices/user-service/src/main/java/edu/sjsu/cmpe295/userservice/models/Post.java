package edu.sjsu.cmpe295.userservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="post")
public class Post {
    @Schema(description = "Unique identifier for a post. Automatic generated.", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Post title", example = "Hello", required = true)
    @NotNull
    @Column(name="title")
    @Length(max = 500)
    private String title;

    @Schema(description = "Post content", example = "xxxxxxxxxxxx", required = true)
    @NotNull
    @Column(name="content")
    private String content;

    @Schema(description = "Author Id", example = "1", required = true)
    @NotNull
    @Column(name="author_id")
    private Long authorId;

    @Schema(description = "Post status", example = "active", required = true)
    @Column(name="status")
    @Length(max = 50)
    private String status;

    @Schema(description = "Post privacy", example = "public")
    @Column(name="privacy")
    @Length(max = 50)
    private String privacy;

    @Schema(description = "Post created time", example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="creation_time")
    private LocalDateTime creationTime = LocalDateTime.now();

    @Schema(description = "Post update time", example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="update_time")
    private LocalDateTime updateTime = LocalDateTime.now();
}
