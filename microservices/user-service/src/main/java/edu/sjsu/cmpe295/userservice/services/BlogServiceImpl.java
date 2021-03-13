package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.exceptions.NotFoundException;
import edu.sjsu.cmpe295.userservice.models.Comment;
import edu.sjsu.cmpe295.userservice.models.Likes;
import edu.sjsu.cmpe295.userservice.models.Post;
import edu.sjsu.cmpe295.userservice.models.Tag;
import edu.sjsu.cmpe295.userservice.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService{
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final LikesRepository likesRepository;

    public enum Status{
        ACTIVE("active"), DELETE("delete");
        private String name;
        Status(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }

    @Override
    public List<Post> getPosts(Long userId) {
        return postRepository.findAllByAuthorId(userId);
    }

    @Override
    public Post addPost(Post post) {
        if(userRepository.findById(post.getAuthorId()).isPresent()){
            post.setStatus(Status.ACTIVE.getName());
            return postRepository.save(post);
        }else{
            throw new NotFoundException("User not found");
        }

    }

    @Override
    public Post deletePostContent(Long postId) {
        if(postRepository.findById(postId).isPresent()){
            Post post = postRepository.findById(postId).get();
            post.setStatus(Status.DELETE.getName());
            return postRepository.save(post);
        }else{
            throw new NotFoundException("Post not found");
        }
    }

    @Override
    public List<Comment> getComments(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public Comment addComment(Comment comment) {
        if(userRepository.findById(comment.getAuthorId()).isPresent()){
            if(postRepository.findById(comment.getPostId()).isPresent()){
                comment.setStatus(Status.ACTIVE.getName());
                return commentRepository.save(comment);
            }else{
                throw new NotFoundException("Post not found");
            }
        }else{
            throw new NotFoundException("User not found");
        }

    }

    @Override
    public Comment deleteCommentContent(Long commentId) {
        if(commentRepository.findById(commentId).isPresent()){
            Comment comment = commentRepository.findById(commentId).get();
            comment.setStatus(Status.DELETE.getName());
            return commentRepository.save(comment);
        }else{
            throw new NotFoundException("Comment not found");
        }
    }

    @Override
    public List<Tag> getTags(Long postId) {
        return tagRepository.findAllByPostId(postId);
    }

    @Override
    public void addTags(List<Tag> tags) {
        for(Tag tag: tags) {
            if (postRepository.findById(tag.getPostId()).isPresent()) {
                tagRepository.save(tag);
            } else {
                throw new NotFoundException("Post not found");
            }
        }
    }

    @Override
    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }

    @Override
    public Likes addLikes(Likes likes) {
        if(userRepository.findById(likes.getUserId()).isPresent()){
            if(postRepository.findById(likes.getPostId()).isPresent()){
                return likesRepository.save(likes);
            }else{
                throw new NotFoundException("Post not found");
            }
        }else {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public void deleteLikes(Likes likes) {
        if(userRepository.findById(likes.getUserId()).isPresent()){
            if(postRepository.findById(likes.getPostId()).isPresent()){
                likesRepository.delete(likes);
            }else{
                throw new NotFoundException("Post not found");
            }
        }else {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public Integer getLikesCount(Long postId) {
        if(postRepository.findById(postId).isPresent()){
            return likesRepository.countAllByPostId(postId);
        }else{
            throw new NotFoundException("Post not found");
        }
    }
}
