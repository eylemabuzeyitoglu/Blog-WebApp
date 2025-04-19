package com.eylemabz.Blog.blog;

import com.eylemabz.Blog.exceptions.BlogNotFoundException;
import com.eylemabz.Blog.exceptions.BlogUpdateException;
import com.eylemabz.Blog.exceptions.UserNotFoundException;
import com.eylemabz.Blog.user.User;
import com.eylemabz.Blog.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository,BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogMapper = blogMapper;
    }

    public List<BlogResponse> getAllBlog(){
        return blogRepository.findAll()
                .stream()
                .map(blog -> blogMapper.toBlogResponse(blog,blog.getBlogId()))
                .collect(Collectors.toList());
    }

    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));

        return blogMapper.toBlogResponse(blog,blog.getBlogId());

    }

    public List<BlogResponse> getTop5Blog(){
        return  blogRepository.findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(blog -> blogMapper.toBlogResponse(blog, blog.getBlogId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public BlogResponse createBlog(BlogRequest blogRequest) {
        Blog blog = blogMapper.toBlogEntity(blogRequest);

        if(blog.getFullContent() != null){
            String content = blog.getFullContent();
            blog.setShortContent(content.length() > 100 ? content.substring(0,100) + "...": content);
        }
        Blog saveBlog = blogRepository.save(blog);
        return blogMapper.toBlogResponse(saveBlog,saveBlog.getBlogId());
    }


    @Transactional
    public BlogResponse updateBlog(Long blogId,BlogRequest blogRequest){
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Güncellenecek blog bulunamadı"));

        if (blogRequest.getTitle() != null && !blogRequest.getTitle().isEmpty()) {
            blog.setTitle(blogRequest.getTitle());
        }

        if (blogRequest.getImgUrl() != null && !blogRequest.getImgUrl().isEmpty()) {
            blog.setImgUrl(blogRequest.getImgUrl());
        }
        if (blogRequest.getFullContent() != null && !blogRequest.getFullContent().isEmpty()) {
            blog.setFullContent(blogRequest.getFullContent());
            blog.setShortContent(blogRequest.getFullContent().length() > 100 ?
                    blogRequest.getFullContent().substring(0, 100) + "..." : blogRequest.getFullContent());
        }
       Blog updated = blogRepository.save(blog);

        return blogMapper.toBlogResponse(updated,updated.getBlogId());
    }

    @Transactional
    public void deleteBlog(Long blogId){
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Silinecek blog bulunamadı"));
        blogRepository.delete(blog);

    }


    public void likeBlog(Long blogId, Long userId) {
        updateBlogLikes(blogId, userId, true);
    }

    public void unlikeBlog(Long blogId, Long userId) {
        updateBlogLikes(blogId, userId, false);
    }


    public void updateBlogLikes(Long blogId, Long userId,boolean isLike) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User bulunamadı"));

        if(isLike){
            if (blog.getLikedByUsers().contains(user)) {
                throw new BlogUpdateException("Bu blog daha önce favorilendi");
            }
            blog.getLikedByUsers().add(user);
        }else {
            if (!blog.getLikedByUsers().contains(user)) {
                throw new BlogUpdateException("You have not liked this blog yet");
            }
            blog.getLikedByUsers().remove(user);
        }
        blogRepository.save(blog);

    }

    public int getLikeCount(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog bulunamadı"));
        return blog.getLikedByUsers().size();
    }


}


























