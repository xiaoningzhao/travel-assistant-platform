package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.Tag;
import edu.sjsu.cmpe295.userservice.models.TagId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, TagId> {
    List<Tag> findAllByPostId(Long postId);
}
