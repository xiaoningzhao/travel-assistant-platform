package com.travelassistantplatform.travelreview.service.impl;

import com.travelassistantplatform.travelreview.enums.ResultEnum;
import com.travelassistantplatform.travelreview.exception.NotFoundException;
import com.travelassistantplatform.travelreview.exception.PersistenceFailureException;
import com.travelassistantplatform.travelreview.exception.TravelReviewException;
import com.travelassistantplatform.travelreview.model.UserTripHistory;
import com.travelassistantplatform.travelreview.repository.UserTripHistoryRepository;
import com.travelassistantplatform.travelreview.service.UserTripHistoryService;
import com.travelassistantplatform.travelreview.utils.UserTripIdObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserTripHistoryServiceImpl implements UserTripHistoryService {
    @Autowired
    private UserTripHistoryRepository userTripHistoryRepository;

    @Override
    public UserTripHistory save(UserTripHistory userTripHistory) {
        UserTripHistory savedUserTripHistory = userTripHistoryRepository.save(userTripHistory);
        if (savedUserTripHistory == null) {
            throw new PersistenceFailureException(ResultEnum.USER_TRIP_HISTORY_SAVING_FAILED);
        }
        return savedUserTripHistory;
    }

    @Override
    public List<UserTripHistory> findAllUserTripHistory() {
        return userTripHistoryRepository.findAll();
    }

    @Override
    public UserTripIdObject findRecordsByUserId(Long userId) {
        List<UserTripHistory> tripHistoryListList = userTripHistoryRepository.findUserTripHistoriesByUserId(userId);
        if (tripHistoryListList == null || tripHistoryListList.size() == 0){
            log.error("[UserTripHistoryServiceImpl::findOneByUserId] Trip details not found. tripId:{}", userId);
            throw new NotFoundException(ResultEnum.USER_HAS_NO_TRIP_HISTORY);
        }

        UserTripIdObject userTripIdObject = new UserTripIdObject();
        userTripIdObject.setUserId(userId);

        for (UserTripHistory record:tripHistoryListList) {
            userTripIdObject.getTripList().add(record.getTripId());
        }
        return userTripIdObject;
    }
}
