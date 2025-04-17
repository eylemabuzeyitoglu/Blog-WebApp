package com.eylemabz.Blog.user;

import com.eylemabz.Blog.blog.Blog;
import com.eylemabz.Blog.exceptions.UserAlreadyExistException;
import com.eylemabz.Blog.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user){
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistException("Kullanıcı mevcut");
                });
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Transactional
    public void updateUserInfo(Long userId, String userName, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        if (userName != null && !userName.isEmpty()) {
            user.setUserName(userName);
        }
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));
        userRepository.deleteById(userId);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));
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

    public Set<Blog> getLikedBlogsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı! " ));
        return user.getLikedBlogs();
    }

}




























