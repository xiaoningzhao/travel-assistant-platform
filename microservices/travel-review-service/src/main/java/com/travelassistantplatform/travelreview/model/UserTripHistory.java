package com.travelassistantplatform.travelreview.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
@Getter
@Setter
public class UserTripHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long tripId;

    public UserTripHistory(Long userId, Long tripId) {
        this.userId = userId;
        this.tripId = tripId;
    }
}
