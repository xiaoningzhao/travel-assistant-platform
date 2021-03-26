package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthorIdAndStatusOrderByCreationTimeDesc(Long authorId, String status);
    List<Post> findAllByAuthorIdInAndPrivacyInAndStatusOrderByCreationTimeDesc(List<Long> authorIds, List<String> privacy, String status);
    List<Post> findAllByAuthorIdInAndPrivacyInAndStatusOrderByCreationTimeDesc(List<Long> authorIds, List<String> privacy, String status, Pageable pageable);
}
