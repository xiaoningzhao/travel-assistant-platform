package com.travelassistantplatform.travelreview.service.impl;

import com.travelassistantplatform.travelreview.enums.ResultEnum;
import com.travelassistantplatform.travelreview.exception.TravelReviewException;
import com.travelassistantplatform.travelreview.model.TripDetails;
import com.travelassistantplatform.travelreview.repository.TripDetailsRepository;
import com.travelassistantplatform.travelreview.service.TripDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TripDetailsServiceImpl implements TripDetailsService {
    @Autowired
    private TripDetailsRepository tripDetailsRepository;

    @Override
    public TripDetails save(TripDetails tripDetails) {
        TripDetails savedTripDetails = tripDetailsRepository.save(tripDetails);
        if (savedTripDetails == null){
            throw new TravelReviewException(ResultEnum.TRIP_SAVING_FAILED);
        }
        return savedTripDetails;
    }

    @Override
    public List<TripDetails> findAllTripDetails() {
        return tripDetailsRepository.findAll();
    }

    @Override
    public TripDetails findOneByTripId(Long tripId) {
        Optional<TripDetails> tripDetailsOptional = tripDetailsRepository.findById(tripId);
        if (! tripDetailsOptional.isPresent()){
            log.error("[TripDetailsServiceImpl::findOneByTripId] Trip details not found. tripId:{}", tripId);
            throw new TravelReviewException(ResultEnum.TRIP_NOT_FOUND);
        }
        return tripDetailsOptional.get();
    }
}
