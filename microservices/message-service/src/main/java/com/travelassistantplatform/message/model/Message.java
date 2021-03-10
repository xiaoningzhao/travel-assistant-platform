package com.travelassistantplatform.message.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;
    private Long chatId;
    private Long userId;
    @Lob
    private String messageText;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
}
