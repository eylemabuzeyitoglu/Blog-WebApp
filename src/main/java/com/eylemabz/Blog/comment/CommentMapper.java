package com.eylemabz.Blog.comment;

import com.eylemabz.Blog.blog.Blog;
import com.eylemabz.Blog.user.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentResponse toCommentResponse(Comment comment){
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .comment(comment.getComment())
                .blogId(comment.getBlog().getBlogId())
                .userId(comment.getUser().getUserId())
                .build();
    }

    public Comment toCommentEntity(CommentRequest commentRequest){
        Comment comment = new Comment();
        User user = new User();
        Blog blog = new Blog();

        user.setUserId(commentRequest.getUserId());
        blog.setBlogId(commentRequest.getBlogId());

        comment.setUser(user);
        comment.setBlog(blog);
        comment.setComment(commentRequest.getComment());

        return comment;
    }
}

