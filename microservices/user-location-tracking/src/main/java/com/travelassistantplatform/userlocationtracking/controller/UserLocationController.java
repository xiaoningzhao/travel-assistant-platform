package com.travelassistantplatform.userlocationtracking.controller;

import com.travelassistantplatform.userlocationtracking.model.UserLocation;
import com.travelassistantplatform.userlocationtracking.service.UserLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/userlocationtracking")
public class UserLocationController {
    @Autowired
    private UserLocationService userLocationService;

    /** save */
    @PostMapping(value = "/save", produces = "application/json")
    public ResponseEntity<UserLocation> save(@RequestBody UserLocation userLocation){
        UserLocation savedUserLocation = userLocationService.save(userLocation);
        return ResponseEntity.ok(savedUserLocation);
    }

    /** find latest one */
    @GetMapping(value = "/getLatestOne", produces = "application/json")
    public ResponseEntity<UserLocation> findLatestOne(@RequestParam(value = "userId") Long userId){
        UserLocation retrievedUserLocation = userLocationService.findLatestOne(userId);
        return ResponseEntity.ok(retrievedUserLocation);
    }

    /** find previous locations */
    @GetMapping(value = "/getPrevious", produces = "application/json")
    public List<UserLocation> findPreviousLocations(@RequestParam(value = "userId") Long userId){
        return userLocationService.findPreviousUserLocations(userId);
    }
}
