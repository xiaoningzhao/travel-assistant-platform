package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthorId(Long authorId);
}
