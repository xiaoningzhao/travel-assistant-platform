package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.Likes;
import edu.sjsu.cmpe295.userservice.models.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {
    Integer countAllByPostId(Long postId);
    Likes findByUserIdAndPostId(Long userId, Long postId);
}
