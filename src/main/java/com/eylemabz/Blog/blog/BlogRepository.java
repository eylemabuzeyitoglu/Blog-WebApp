package com.eylemabz.Blog.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {

    List<Blog> findTop5ByOrderByCreatedAtDesc();
}
