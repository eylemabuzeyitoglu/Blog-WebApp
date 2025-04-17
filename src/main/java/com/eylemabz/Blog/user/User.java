package com.eylemabz.Blog.user;

import com.eylemabz.Blog.blog.Blog;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank
    private String userName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @ManyToMany(mappedBy = "likedByUsers")
    private Set<Blog> likedBlogs;

}
