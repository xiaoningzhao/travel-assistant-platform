package com.travelassistantplatform.travelreview.repository;

import com.travelassistantplatform.travelreview.model.UserTripHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTripHistoryRepository extends JpaRepository<UserTripHistory, Long> {
    List<UserTripHistory> findUserTripHistoriesByUserId(Long userId);
}
