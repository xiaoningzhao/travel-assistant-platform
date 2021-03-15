package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.models.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {
    List<Post> getPosts(Long userId);
    List<Post> getFriendsPosts(Long userId);
    Post getPostById(Long postId);
    Post addPost(Post post);
    Post deletePostContent(Long postId);

    List<Comment> getComments(Long postId);
    Integer getCommentCount(Long postId);
    Comment addComment(Comment comment);
    Comment deleteCommentContent(Long commentId);

    List<Tag> getTags(Long postId);
    void addTags(List<Tag> tags);
    void deleteTag(Tag tag);

    Likes addLikes(Likes likes);
    void deleteLikes(Likes likes);
    Integer getLikesCount(Long postId);
    Boolean getIfLikes(Long userId, Long postId);

    String uploadPostImage(MultipartFile multipartFile, Long postId, Long userId);
    void addPostImage(Long postId, String imageUrl);
    List<PostImage> getImagesUrl(Long postId);
}
