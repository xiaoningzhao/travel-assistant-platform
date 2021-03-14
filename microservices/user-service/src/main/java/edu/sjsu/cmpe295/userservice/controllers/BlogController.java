package edu.sjsu.cmpe295.userservice.controllers;

import edu.sjsu.cmpe295.userservice.exceptions.ConflictException;
import edu.sjsu.cmpe295.userservice.models.Comment;
import edu.sjsu.cmpe295.userservice.models.Likes;
import edu.sjsu.cmpe295.userservice.models.Post;
import edu.sjsu.cmpe295.userservice.models.Tag;
import edu.sjsu.cmpe295.userservice.services.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blog")
@Validated
public class BlogController {
    private final BlogService blogService;

    @Operation(summary = "Retrieve user's posts", description = "Retrieve user's posts", tags = { "Blog Service" })
    @GetMapping("/post/{userId}")
    public List<Post> getPosts(@PathVariable Long userId) {
        return blogService.getPosts(userId);
    }

    @Operation(summary = "Post an article", description = "Post an article", tags = { "Blog Service" })
    @PostMapping("/post")
    public Post addPosts(@Valid @RequestBody Post post) {
        return blogService.addPost(post);
    }

    @Operation(summary = "Delete a post", description = "Delete a post", tags = { "Blog Service" })
    @DeleteMapping("/post/{postId}")
    public Post deletePost(@PathVariable Long postId) {
        return blogService.deletePostContent(postId);
    }

    @Operation(summary = "Retrieve post's comments", description = "Retrieve post's comments", tags = { "Blog Service" })
    @GetMapping("/comment/{postId}")
    public List<Comment> getComments(@PathVariable Long postId) {
        return blogService.getComments(postId);
    }

    @Operation(summary = "Post a comment", description = "Post a comment", tags = { "Blog Service" })
    @PostMapping("/comment")
    public Comment addComment(@Valid @RequestBody Comment comment) {
        return blogService.addComment(comment);
    }

    @Operation(summary = "Delete a comment", description = "Delete a comment", tags = { "Blog Service" })
    @DeleteMapping("/comment/{commentId}")
    public Comment deleteComment(@PathVariable Long commentId) {
        return blogService.deleteCommentContent(commentId);
    }

    @Operation(summary = "Retrieve post's tags", description = "Retrieve post's tags", tags = { "Blog Service" })
    @GetMapping("/tag/{postId}")
    public List<Tag> getTags(@PathVariable Long postId) {
        return blogService.getTags(postId);
    }

    @Operation(summary = "Add tags", description = "Add tags", tags = { "Blog Service" })
    @PostMapping("/tag")
    public void addTags(@Valid @RequestBody List<Tag> tags) {
        blogService.addTags(tags);
    }

    @Operation(summary = "Delete a tag", description = "Delete a tag", tags = { "Blog Service" })
    @DeleteMapping("/tag")
    public void deleteTag(@Valid @RequestBody Tag tag) {
        blogService.deleteTag(tag);
    }

    @Operation(summary = "Retrieve post's likes", description = "Retrieve post's likes", tags = { "Blog Service" })
    @GetMapping("/likes/count/{postId}")
    public Integer getLikesCount(@PathVariable Long postId) {
        return blogService.getLikesCount(postId);
    }

    @Operation(summary = "Add a like", description = "Add a like", tags = { "Blog Service" })
    @PostMapping("/likes")
    public Likes addLikes(@Valid @RequestBody Likes likes) {
        return blogService.addLikes(likes);
    }

    @Operation(summary = "Delete a like", description = "Delete a like", tags = { "Blog Service" })
    @DeleteMapping("/likes")
    public void deleteLIkes(@Valid @RequestBody Likes likes) {
        blogService.deleteLikes(likes);
    }

    @PostMapping("/image/{userId}/{postId}")
    public void uploadPostImage(@RequestParam("image") MultipartFile multipartFile, @PathVariable Long postId, @PathVariable Long userId){
        if (multipartFile.isEmpty()) {
            throw new ConflictException("Image file is empty");
        }
        String imageUrl = blogService.uploadPostImage(multipartFile, postId, userId);
        blogService.addPostImage(postId, imageUrl);
    }

}
