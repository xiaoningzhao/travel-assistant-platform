package com.travelassistantplatform.userlocationtracking.repository;

import com.travelassistantplatform.userlocationtracking.model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    /** find latest user location */
    UserLocation findTopByUserIdOrderByCreatedAtDesc(Long userId);

    /** find all user locations ordered by timestamp DESC, limited 1000 */
    List<UserLocation> findTop1000ByUserIdOrderByCreatedAtDesc(Long userId);
}
