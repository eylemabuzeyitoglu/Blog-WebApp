package com.eylemabz.Blog.blog;

import com.eylemabz.Blog.exceptions.BlogCreationException;
import com.eylemabz.Blog.exceptions.BlogNotFoundException;
import com.eylemabz.Blog.exceptions.UserNotFoundException;
import com.eylemabz.Blog.user.User;
import com.eylemabz.Blog.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public List<Blog> getAllBlog(){
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));
    }

    public List<Blog> getTop5Blog(){
        return blogRepository.findTop5ByOrderByCreatedAtDesc();
    }

    @Transactional
    public Blog createBlog(Blog blog) {
        try {
            if (blog.getFullContent() != null) {
                String content = blog.getFullContent();
                blog.setShortContent(content.length() > 100 ? content.substring(0, 100) + "..." : content);
            }

            return blogRepository.save(blog);

        } catch (Exception e) {
            throw new BlogCreationException("Blog oluşturulurken hata oluştu!!");
        }
    }


    @Transactional
    public Blog updateBlog(Long id,String title, String content){
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Güncellenecek blog bulunamadı"));

        if (title != null && !title.isEmpty()) {
            blog.setTitle(title);
        }
        if (content != null && !content.isEmpty()) {
            blog.setFullContent(content);
            blog.setShortContent(content.length() > 100 ? content.substring(0, 100) + "..." : content);
        }

        return blogRepository.save(blog);
    }

    @Transactional
    public void deleteBlog(Long blogId){
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Silinecek blog bulunamadı"));
        blogRepository.delete(blog);

    }

    public void likeBlog(Long blogId, Long userId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User bulunamadı"));

        if (!blog.getLikedByUsers().contains(user)) {
            blog.getLikedByUsers().add(user);
            blogRepository.save(blog);
        }
    }

    public void unlikeBlog(Long blogId, Long userId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User bulunamadı"));

        if (blog.getLikedByUsers().contains(user)) {
            blog.getLikedByUsers().remove(user);
            blogRepository.save(blog);
        }
    }
    public int getLikeCount(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));
        return blog.getLikedByUsers().size();
    }


}


























