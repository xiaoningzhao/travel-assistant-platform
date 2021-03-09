package com.travelassistantplatform.travelreview.repository;

import com.travelassistantplatform.travelreview.model.TripDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripDetailsRepository extends JpaRepository<TripDetails, Long> {
    TripDetails findTripDetailsByTripId(Long tripId);
}
