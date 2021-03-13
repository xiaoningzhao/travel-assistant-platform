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
@Table(name="comment")
public class Comment {
    @Schema(description = "Unique identifier for a comment. Automatic generated.", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Comment content", example = "xxxxxxxxxxxx", required = true)
    @NotNull
    @Column(name="content")
    private String content;

    @Schema(description = "Post Id", example = "1", required = true)
    @NotNull
    @Column(name="post_id")
    private Long postId;

    @Schema(description = "Comment status", example = "active", required = true)
    @Column(name="status")
    @Length(max = 50)
    private String status;

    @Schema(description = "Author Id", example = "1", required = true)
    @NotNull
    @Column(name="author_id")
    private Long authorId;

    @Schema(description = "Post created time", example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="creation_time")
    private LocalDateTime creationTime = LocalDateTime.now();

}
