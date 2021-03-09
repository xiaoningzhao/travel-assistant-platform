package com.travelassistantplatform.travelreview.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
@Getter
@Setter
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long destinationId;
    private String googleLocationCode;

    public Destination(Long destinationId, String googleLocationCode) {
        this.destinationId = destinationId;
        this.googleLocationCode = googleLocationCode;
    }
}
