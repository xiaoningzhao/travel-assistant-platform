package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.models.Comment;
import edu.sjsu.cmpe295.userservice.models.Likes;
import edu.sjsu.cmpe295.userservice.models.Post;
import edu.sjsu.cmpe295.userservice.models.Tag;

import java.util.List;

public interface BlogService {
    List<Post> getPosts(Long userId);
    Post addPost(Post post);
    Post deletePostContent(Long postId);

    List<Comment> getComments(Long postId);
    Comment addComment(Comment comment);
    Comment deleteCommentContent(Long commentId);

    List<Tag> getTags(Long postId);
    void addTags(List<Tag> tags);
    void deleteTag(Tag tag);

    Likes addLikes(Likes likes);
    void deleteLikes(Likes likes);
    Integer getLikesCount(Long postId);
}
