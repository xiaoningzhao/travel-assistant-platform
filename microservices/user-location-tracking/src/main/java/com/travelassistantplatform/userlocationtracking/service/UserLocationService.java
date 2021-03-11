package com.travelassistantplatform.userlocationtracking.service;

import com.travelassistantplatform.userlocationtracking.model.UserLocation;

import java.util.List;

public interface UserLocationService {
    /** save */
    UserLocation save(UserLocation userLocation);

    /** find latest user location */
    UserLocation findLatestOne(Long userId);

    /** find all user locations ordered by timestamp DESC, limited at 1000. */
    List<UserLocation> findPreviousUserLocations(Long userId);
}
