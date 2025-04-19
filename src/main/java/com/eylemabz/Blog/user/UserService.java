package com.eylemabz.Blog.user;

import com.eylemabz.Blog.blog.BlogMapper;
import com.eylemabz.Blog.blog.BlogResponse;
import com.eylemabz.Blog.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final BlogMapper blogMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, BlogMapper blogMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.blogMapper = blogMapper;
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        User user = userMapper.toUserEntity(userRequest);

        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(hashedPassword);

        User created = userRepository.save(user);
        return userMapper.toUserResponse(created);
    }

    @Transactional
    public UserResponse updateUserInfo(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        if (userRequest.getUserName() != null && !userRequest.getUserName() .isEmpty()) {
            user.setUserName(userRequest.getUserName());
        }
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
            user.setEmail(userRequest.getEmail());
        }

        User updated = userRepository.save(user);
        return userMapper.toUserResponse(updated);
    }

    @Transactional
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));
        userRepository.deleteById(userId);
    }

    public List<UserResponse> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.toUserResponse(user))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long userId){
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        return userMapper.toUserResponse(user);

    }

    public String login(String userName,String rawPassword){
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return "Giriş başarılı!";
        } else {
            return "Hatalı şifre!";
        }

    }

    public void resetPassword(Long userId, String password){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public Set<BlogResponse> getLikedBlogsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı! " ));
        return user.getLikedBlogs()
                .stream()
                .map(blog -> blogMapper.toBlogResponse(blog, blog.getBlogId()))
                .collect(Collectors.toSet());
    }

}




























