package com.eylemabz.Blog.comment;

import com.eylemabz.Blog.blog.Blog;
import com.eylemabz.Blog.blog.BlogRepository;
import com.eylemabz.Blog.exceptions.CommentNotFoundException;
import com.eylemabz.Blog.exceptions.UnAuthorizedException;
import com.eylemabz.Blog.user.User;
import com.eylemabz.Blog.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, BlogRepository blogRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.commentMapper = commentMapper;
    }

    @Transactional
    public CommentResponse createComment(CommentRequest commentRequest){
        Blog blog = blogRepository.findById(commentRequest.getBlogId())
                .orElseThrow(() -> new RuntimeException("Blog bulunamadı"));

        User user = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Comment comment = new Comment();
        comment.setBlog(blog);
        comment.setUser(user);
        comment.setComment(commentRequest.getComment());

        Comment saved = commentRepository.save(comment);
        return commentMapper.toCommentResponse(saved);
    }


    @Transactional
    public CommentResponse updateComment(Long commentId, String commentContent, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Yorum bulunamadı"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnAuthorizedException("Yalnızca kendi yorumunuzu güncelleyebilirsiniz.");
        }

        if (commentContent != null && !commentContent.isEmpty()) {
            comment.setComment(commentContent);
        }

        Comment updated = commentRepository.save(comment);
        return commentMapper.toCommentResponse(updated);
    }


    @Transactional
    public void deleteComment(Long commentId,Long userId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Yorum Bulunamadı."));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnAuthorizedException("Yalnızca kendi yorumunuzu silebilirsiniz.");
        }
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getAllComment(){
        return commentRepository.findAll();
    }

    public Set<Blog> getBlogsCommentedByUser(Long userId){
        List<Comment> comments = commentRepository.findByUser_userId(userId);

        return comments.stream()
                .map(Comment::getBlog)
                .collect(Collectors.toSet());
    }
}




































