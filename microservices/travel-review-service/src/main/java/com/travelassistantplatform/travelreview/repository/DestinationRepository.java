package com.travelassistantplatform.travelreview.repository;

import com.travelassistantplatform.travelreview.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Destination findDestinationByDestinationId(Long destinationId);
}
