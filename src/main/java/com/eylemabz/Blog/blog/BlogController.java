package com.eylemabz.Blog.blog;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public void createBlog(@RequestBody @Valid Blog blog){
        blogService.createBlog(blog);
    }

    @PatchMapping
    public void updateBlog(@RequestParam long id,
                           @RequestParam String title,
                           @RequestParam String content){
        blogService.updateBlog(id,title,content);
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
    }

    @GetMapping
    public List<Blog> getAllBlog(){
       return  blogService.getAllBlog();
    }

    @GetMapping("/{id}")
    public Blog getBlogById(@PathVariable Long id){
        return blogService.getBlogById(id);
    }

    @GetMapping("/index")
    public List<Blog> getTop5Blog(){
        return blogService.getTop5Blog();
    }


}
