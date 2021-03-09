package com.travelassistantplatform.travelreview.service;

import com.travelassistantplatform.travelreview.model.Destination;

import java.util.List;

public interface DestinationService {
    /** save a destination to database */
    Destination save(Destination destination);

    /** find a single destination by destinationId */
    Destination findOneByDestinationId(Long destinationId);

    /** find all destinations in database */
    List<Destination> findAllDestinations();
}
