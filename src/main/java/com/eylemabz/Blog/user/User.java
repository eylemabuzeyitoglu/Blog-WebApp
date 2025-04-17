package com.eylemabz.Blog.user;

import com.eylemabz.Blog.blog.Blog;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String role;

    @ManyToMany(mappedBy = "likedByUsers")
    private Set<Blog> likedBlogs;

}
