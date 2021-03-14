package edu.sjsu.cmpe295.userservice.repositories;

import edu.sjsu.cmpe295.userservice.models.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

}
