package com.travelassistantplatform.userlocationtracking.service.impl;

import com.travelassistantplatform.userlocationtracking.enums.ResultEnum;
import com.travelassistantplatform.userlocationtracking.exception.NotFoundException;
import com.travelassistantplatform.userlocationtracking.exception.PersistenceFailureException;
import com.travelassistantplatform.userlocationtracking.model.UserLocation;
import com.travelassistantplatform.userlocationtracking.repository.UserLocationRepository;
import com.travelassistantplatform.userlocationtracking.service.UserLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLocationServiceImpl implements UserLocationService {
    @Autowired
    private UserLocationRepository userLocationRepository;

    @Override
    public UserLocation save(UserLocation userLocation) {
        UserLocation savedUserLocation = userLocationRepository.save(userLocation);
        if (savedUserLocation == null) {
            throw new PersistenceFailureException(ResultEnum.USER_LOCATION_SAVING_FAILED);
        }
        return savedUserLocation;
    }

    @Override
    public UserLocation findLatestOne(Long userId) {
        UserLocation retrievedUserLocation = userLocationRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
        if (retrievedUserLocation == null){
            throw new NotFoundException(ResultEnum.USER_HAS_NO_LOCATION_RECORDS);
        }
        return retrievedUserLocation;
    }

    @Override
    public List<UserLocation> findPreviousUserLocations(Long userId) {
        List<UserLocation> retrievedUserLocationList = userLocationRepository.findTop1000ByUserIdOrderByCreatedAtDesc(userId);
        if (retrievedUserLocationList.isEmpty()) {
            throw new NotFoundException(ResultEnum.USER_HAS_NO_LOCATION_RECORDS);
        }
        return retrievedUserLocationList;
    }
}
