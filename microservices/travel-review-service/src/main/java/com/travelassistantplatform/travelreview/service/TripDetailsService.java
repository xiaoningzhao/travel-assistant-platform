package com.travelassistantplatform.travelreview.service;

import com.travelassistantplatform.travelreview.model.TripDetails;

import java.util.List;

public interface TripDetailsService {
    TripDetails save(TripDetails tripDetails);

    /** find all trips in the database */
    List<TripDetails> findAllTripDetails();

    /** find a trip by tripId */
    TripDetails findOneByTripId(Long tripId);
}
