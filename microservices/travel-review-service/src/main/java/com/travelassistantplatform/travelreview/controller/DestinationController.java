package com.travelassistantplatform.travelreview.controller;

import com.travelassistantplatform.travelreview.model.Destination;
import com.travelassistantplatform.travelreview.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/destination")
public class DestinationController {
    @Autowired
    private DestinationService destinationService;

    /** save */
    @PostMapping(path = "/save", produces = "application/json")
    public ResponseEntity<Destination> save(@RequestParam(value = "googleLocationCode") String googleLocationCode){
        Destination newDestination = new Destination();
        newDestination.setGoogleLocationCode(googleLocationCode);
        Destination savedDestination = destinationService.save(newDestination);
        return ResponseEntity.ok(savedDestination);
    }

    /** find one */
    @GetMapping(value = "/getOneById", produces = "application/json")
    public ResponseEntity<Destination> getDestinationById(@RequestParam(value = "destinationId") Long destinationId){
        Destination retrievedDestination = destinationService.findOneByDestinationId(destinationId);
        return ResponseEntity.ok(retrievedDestination);
    }

    /** find all */
    @GetMapping(value = "/getAll", produces = "application/json")
    public List<Destination> getAllDestinations(){
        return destinationService.findAllDestinations();
    }
}
