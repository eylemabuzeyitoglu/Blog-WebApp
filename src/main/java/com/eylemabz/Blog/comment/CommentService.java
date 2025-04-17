package com.eylemabz.Blog.comment;

import com.eylemabz.Blog.blog.Blog;
import com.eylemabz.Blog.blog.BlogRepository;
import com.eylemabz.Blog.exceptions.BlogNotFoundException;
import com.eylemabz.Blog.exceptions.CommentNotFoundException;
import com.eylemabz.Blog.exceptions.UnAuthorizedException;
import com.eylemabz.Blog.exceptions.UserNotFoundException;
import com.eylemabz.Blog.user.User;
import com.eylemabz.Blog.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }


    @Transactional
    public Comment createComment(Long userId,Long blogId,Comment comment){
        Blog blog =  blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        comment.setBlog(blog);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long userId,Long blogId,Long commentId,String commentContent) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Yorum bulunamadı"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnAuthorizedException("Yalnızca kendi yorumunuzu güncelleyebilirsiniz.");
        }else{
            if (commentContent != null && !commentContent.isEmpty()) {
                comment.setComment(commentContent);
            }
        }
        return commentRepository.save(comment);
    }
}
