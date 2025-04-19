package com.eylemabz.Blog.user;

import com.eylemabz.Blog.blog.BlogResponse;
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
    public void createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }

    @PatchMapping
    public void updateUserInfo(@RequestParam Long userId,
                               @RequestBody UserRequest userRequest){
        userService.updateUserInfo(userId,userRequest);
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
    public ResponseEntity<Set<BlogResponse>> getLikedBlogs(@PathVariable Long userId) {
        Set<BlogResponse> likedBlogs = userService.getLikedBlogsByUserId(userId);
        return ResponseEntity.ok(likedBlogs);
    }


}
