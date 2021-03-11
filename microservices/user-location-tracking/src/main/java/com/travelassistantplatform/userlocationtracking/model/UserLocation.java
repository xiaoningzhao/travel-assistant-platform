package com.travelassistantplatform.userlocationtracking.model;

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
@DynamicUpdate
@Getter
@Setter
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;
    private Long userId;
    private Long latitude;
    private Long longitude;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
}
