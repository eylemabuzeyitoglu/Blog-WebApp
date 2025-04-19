package com.eylemabz.Blog.blog;

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
    public void createBlog(@RequestBody BlogRequest blogRequest){
        blogService.createBlog(blogRequest);
    }

    @PatchMapping("{id}")
    public void updateBlog(@PathVariable long id,
                           @RequestBody BlogRequest blogRequest) {
        blogService.updateBlog(id,blogRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
    }

    @GetMapping
    public List<BlogResponse> getAllBlog(){
       return  blogService.getAllBlog();
    }

    @GetMapping("/{id}")
    public BlogResponse getBlogById(@PathVariable Long id){
        return blogService.getBlogById(id);
    }

    @GetMapping("/index")
    public List<BlogResponse> getTop5Blog(){
        return blogService.getTop5Blog();
    }


}
