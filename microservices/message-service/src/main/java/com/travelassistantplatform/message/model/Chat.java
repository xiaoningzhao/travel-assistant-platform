package com.travelassistantplatform.message.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatId;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
}
