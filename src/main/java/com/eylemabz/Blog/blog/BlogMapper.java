package com.eylemabz.Blog.blog;

import org.springframework.stereotype.Component;

@Component
public class BlogMapper {

    public BlogResponse toBlogResponse(Blog blog,Long blogId){
        return BlogResponse.builder()
                .blogId(blog.getBlogId())
                .title(blog.getTitle())
                .fullContent(blog.getFullContent())
                .shortContent(blog.getShortContent())
                .createdAt(blog.getCreatedAt())
                .updateAt(blog.getUpdateAt())
                .build();
    }

    public Blog toBlogEntity(BlogRequest blogRequest){
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setFullContent(blogRequest.getFullContent());
        blog.setShortContent(blogRequest.getShortContent());
        blog.setImgUrl(blogRequest.getImgUrl());

        return blog;
    }
}
