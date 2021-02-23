package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.Friend;
import edu.sjsu.cmpe295.userservice.models.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    List<Friend> findAllByUser1Id(Long user1Id);
}
