package com.travelassistantplatform.travelreview.controller;

import com.travelassistantplatform.travelreview.model.TripDetails;
import com.travelassistantplatform.travelreview.service.TripDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tripdetails")
public class TripDetailController {
    @Autowired
    private TripDetailsService tripDetailsService;

    /** save */
    @PostMapping(path = "/save", produces = "application/json", consumes = "application/json")
    public ResponseEntity<TripDetails> save(@RequestBody TripDetails tripDetails){
        TripDetails savedTripDetails = tripDetailsService.save(tripDetails);
        return ResponseEntity.ok(savedTripDetails);
    }

    /** find by id */
    @GetMapping(path = "/getOneById", produces = "application/json")
    public ResponseEntity<TripDetails> getTripDetailsById(@RequestParam(value = "tripDetailsId") Long tripDetailsId){
        TripDetails retrievedTripDetails = tripDetailsService.findOneByTripId(tripDetailsId);
        return ResponseEntity.ok(retrievedTripDetails);
    }

    /** find all */
    @GetMapping(path = "/getAll", produces = "application/json")
    public List<TripDetails> getAllTripDetails(){ return tripDetailsService.findAllTripDetails(); }
}
