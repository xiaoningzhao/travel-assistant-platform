package com.travelassistantplatform.travelreview.service.impl;

import com.travelassistantplatform.travelreview.enums.ResultEnum;
import com.travelassistantplatform.travelreview.exception.TravelReviewException;
import com.travelassistantplatform.travelreview.model.Destination;
import com.travelassistantplatform.travelreview.repository.DestinationRepository;
import com.travelassistantplatform.travelreview.service.DestinationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DestinationServiceImpl implements DestinationService {
    @Autowired
    private  DestinationRepository destinationRepository;

    @Override
    public Destination save(Destination destination) {
        Destination savedDestination = destinationRepository.save(destination);
        if(savedDestination == null){
            throw new TravelReviewException(ResultEnum.DESTINATION_SAVING_FAILED);
        }
        return savedDestination;
    }

    @Override
    public Destination findOneByDestinationId(Long destinationId) {
        Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);
        if (! destinationOptional.isPresent()){
            log.error("[DestinationServiceImpl::findOneByDestinationId] Destination not found. destinationId:{}", destinationId);
            throw new TravelReviewException(ResultEnum.DESTINATION_NOT_FOUND);
        }
        return destinationOptional.get();
    }

    @Override
    public List<Destination> findAllDestinations() {
        return destinationRepository.findAll();
    }
}
