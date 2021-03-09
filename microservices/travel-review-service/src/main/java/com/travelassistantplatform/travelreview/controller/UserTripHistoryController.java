package com.travelassistantplatform.travelreview.controller;

import com.travelassistantplatform.travelreview.model.UserTripHistory;
import com.travelassistantplatform.travelreview.service.UserTripHistoryService;
import com.travelassistantplatform.travelreview.utils.UserTripIdObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "usertriphistory")
public class UserTripHistoryController {
    @Autowired
    private UserTripHistoryService userTripHistoryService;

    /** save */
    @PostMapping(path = "/save", produces = "application/json")
    public ResponseEntity<UserTripHistory> save(@RequestParam(value = "userId") Long userId, @RequestParam(value = "tripId") Long tripId){
        UserTripHistory newUserTripHistory = new UserTripHistory(userId, tripId);
        UserTripHistory savedUserTripHistory = userTripHistoryService.save(newUserTripHistory);
        return ResponseEntity.ok(savedUserTripHistory);
    }

    /** find all tripIds done by an user id */
    @GetMapping(path = "/getRecordsByUserId", produces = "application/json")
    public UserTripIdObject getUserTripHistoryById(@RequestParam(value = "userId") Long userId){
        return userTripHistoryService.findRecordsByUserId(userId);
    }
}
