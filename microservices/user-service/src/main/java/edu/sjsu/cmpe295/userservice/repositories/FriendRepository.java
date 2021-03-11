package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.Friend;
import edu.sjsu.cmpe295.userservice.models.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    List<Friend> findAllByUser1Id(Long user1Id);

    @Query(value = "select user2Id from Friend where status='1' and user1Id=:user1Id")
    List<Long> findFriendsListByUser1Id(@Param("user1Id") Long user1Id);

    @Query(value = "select user1Id from Friend where status='1' and user2Id=:user2Id")
    List<String> findFriendsListByUser2Id(@Param("user2Id") Long user2Id);
}
