package edu.sjsu.cmpe295.userservice.services;

import edu.sjsu.cmpe295.userservice.exceptions.FileException;
import edu.sjsu.cmpe295.userservice.exceptions.NotFoundException;
import edu.sjsu.cmpe295.userservice.models.*;
import edu.sjsu.cmpe295.userservice.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService{
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final LikesRepository likesRepository;
    private final PostImageRepository postImageRepository;
    private final FriendRepository friendRepository;
    private final UserAvatarRepository userAvatarRepository;

    @Value("${image.upload.path}")
    private String imageUploadPath;

    @Value("${image.static.path}")
    private String staticPath;

    public enum Status{
        ACTIVE("active"), DELETE("delete");
        private final String name;
        Status(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }

    public enum Privacy{
        PUBLIC("public"), FRIENDS("friends"), PRIVATE("private");
        private final String name;
        Privacy(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }

    @Override
    public List<Post> getPosts(Long userId) {
        return postRepository.findAllByAuthorIdAndStatusOrderByCreationTimeDesc(userId, Status.ACTIVE.getName());
    }

    @Override
    public List<Post> getFriendsPosts(Long userId) {
        if(userRepository.findById(userId).isPresent()){
            List<Long> friends = friendRepository.findFriendsListByUser1Id(userId);
            List<String> privacy = new ArrayList<>();
            privacy.add(Privacy.PUBLIC.getName());
            privacy.add(Privacy.FRIENDS.getName());
            return postRepository.findAllByAuthorIdInAndPrivacyInAndStatusOrderByCreationTimeDesc(friends, privacy, Status.ACTIVE.getName());
        }else{
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public List<Post> getAllPosts(Long userId) {
        if(userRepository.findById(userId).isPresent()){
            List<Long> users = friendRepository.findFriendsListByUser1Id(userId);
            users.add(userId);
            List<String> privacy = new ArrayList<>();
            privacy.add(Privacy.PUBLIC.getName());
            privacy.add(Privacy.FRIENDS.getName());
            return postRepository.findAllByAuthorIdInAndPrivacyInAndStatusOrderByCreationTimeDesc(users, privacy, Status.ACTIVE.getName());
        }else{
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public Post getPostById(Long postId) {
        if(postRepository.findById(postId).isPresent()){
            return postRepository.findById(postId).get();
        }else{
            throw new NotFoundException("Post not found");
        }
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
        List<Comment> comments = commentRepository.findAllByPostIdAndStatusOrderByCreationTimeDesc(postId, Status.ACTIVE.getName());
        for(Comment comment: comments){
            if(userRepository.findById(comment.getAuthorId()).isPresent()){
                User user = userRepository.findById(comment.getAuthorId()).get();
                comment.setAuthorFirstName(user.getFirstName());
                comment.setAuthorLastName(user.getLastName());
                if(userAvatarRepository.findById(comment.getAuthorId()).isPresent()){
                    comment.setAuthorAvatarUrl(userAvatarRepository.findById(comment.getAuthorId()).get().getAvatarUrl());
                }else{
                    comment.setAuthorAvatarUrl("");
                }
            }
        }
        return comments;
    }

    @Override
    public Integer getCommentCount(Long postId) {
        if(postRepository.findById(postId).isPresent()){
            return commentRepository.countAllByPostIdAndStatus(postId, Status.ACTIVE.getName());
        }else{
            throw new NotFoundException("Post not found");
        }
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

    @Override
    public Boolean getIfLikes(Long userId, Long postId) {
        if(userRepository.findById(userId).isPresent()){
            if(postRepository.findById(postId).isPresent()){
                return likesRepository.findByUserIdAndPostId(userId, postId) != null;

            }else{
                throw new NotFoundException("Post not found");
            }
        }else {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public String uploadPostImage(MultipartFile multipartFile, Long postId, Long userId) {
        String fileName = multipartFile.getOriginalFilename();
        String suffixName = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String filePath = imageUploadPath +"/"+userId+"/"+postId+"/";

        fileName = UUID.randomUUID() + suffixName;
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            multipartFile.transferTo(file);
            //return file path
            return staticPath + userId + "/" + postId + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("File cannot save");
        }
    }

    @Override
    public void addPostImage(Long postId, String imageUrl) {
        if(postRepository.findById(postId).isPresent()){
            PostImage postImage = new PostImage();
            postImage.setPostId(postId);
            postImage.setImageUrl(imageUrl);
            postImageRepository.save(postImage);

        }else{
            throw new NotFoundException("Post not found");
        }
    }

    @Override
    public List<PostImage> getImagesUrl(Long postId) {
        if(postRepository.findById(postId).isPresent()){
            return postImageRepository.findAllByPostId(postId);

        }else{
            throw new NotFoundException("Post not found");
        }
    }
}
