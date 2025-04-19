package com.eylemabz.Blog.comment;

import com.eylemabz.Blog.blog.Blog;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public CommentResponse createComment(@RequestBody CommentRequest commentRequest){
        return commentService.createComment(commentRequest);
    }


    @PutMapping("/{commentId}")
    public CommentResponse updateComment(@PathVariable Long commentId,
                                         @RequestParam Long userId,
                                         @RequestParam String commentContent){
        return commentService.updateComment(commentId, commentContent, userId);
    }


    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @RequestParam Long userId){
        commentService.deleteComment(commentId, userId);
    }


    @GetMapping
    public List<Comment> getAllComment(){
        return commentService.getAllComment();
    }


    @GetMapping("/user/{userId}/blogs")
    public Set<Blog> getBlogsCommented(@PathVariable Long userId){
        return commentService.getBlogsCommentedByUser(userId);
    }
}
