package com.eylemabz.Blog.user;

import com.eylemabz.Blog.blog.Blog;
import com.eylemabz.Blog.blog.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final BlogService blogService;

    public UserController(UserService userService, BlogService blogService) {
        this.userService = userService;
        this.blogService = blogService;
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }

    @PatchMapping
    public void updateUserInfo(@RequestParam Long userId,
                               @RequestParam String userName,
                               @RequestParam String password){
        userService.updateUserInfo(userId,userName,password);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @PatchMapping("/{id}")
    public void resetPassword(@PathVariable Long userId,
                              @RequestParam String password){
        userService.resetPassword(userId,password);
    }

    @GetMapping("/{userId}/liked-blogs")
    public ResponseEntity<Set<Blog>> getLikedBlogs(@PathVariable Long userId) {
        Set<Blog> likedBlogs = userService.getLikedBlogsByUserId(userId);
        return ResponseEntity.ok(likedBlogs);
    }


}
