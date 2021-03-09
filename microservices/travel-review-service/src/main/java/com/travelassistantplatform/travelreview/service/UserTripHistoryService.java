package com.travelassistantplatform.travelreview.service;

import com.travelassistantplatform.travelreview.model.UserTripHistory;
import com.travelassistantplatform.travelreview.utils.UserTripIdObject;

import java.util.List;

public interface UserTripHistoryService {
    UserTripHistory save(UserTripHistory userTripHistory);

    /** find all user-trip history in the database */
    List<UserTripHistory> findAllUserTripHistory();

    /** find a user-trip record by userId */
    UserTripIdObject findRecordsByUserId(Long userId);
}
