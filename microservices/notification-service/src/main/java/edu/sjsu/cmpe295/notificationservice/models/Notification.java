package edu.sjsu.cmpe295.notificationservice.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
public class Notification {
    @Schema(description = "Unique identifier of the Notification. Automatic generated.", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "To user's ID", example = "1", required = true)
    @NotEmpty(message = "toUserId cannot be empty")
    private String toUserId;

    @Schema(description = "Notification type", example = "alert")
    @Size(max = 50)
    private String type;

    @Schema(description = "Notification title", example = "Title")
    @Size(max = 200)
    private String title;

    @Schema(description = "Notification content", example = "This is a message.")
    @Size(max = 5000)
    private String content;

    @Schema(description = "Identify if a notification has been read", example = "true")
    private boolean isRead;

    @Schema(description = "Notification send timestamp", example = "2021-03-01T21:11:28.188")
    private String timestamp;

    @Schema(description = "Identify if a notification has been received", example = "true")
    private boolean received;
}
