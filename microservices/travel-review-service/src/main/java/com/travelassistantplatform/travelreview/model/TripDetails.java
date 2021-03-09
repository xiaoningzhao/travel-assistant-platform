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
public class TripDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tripId;
    private int groupSize;
    private String destinationId;
    private String startDate;
    private int numberOfDays;
    private String remarks;
    private String chatRecordId;

    public TripDetails(Long tripId, int groupSize, String destinationId, String startDate, int numberOfDays, String remarks, String chatRecordId) {
        this.tripId = tripId;
        this.groupSize = groupSize;
        this.destinationId = destinationId;
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
        this.remarks = remarks;
        this.chatRecordId = chatRecordId;
    }
}
