package com.eylemabz.Blog.blog;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse {

    private Long blogId;
    private String title;
    private String fullContent;
    private String shortContent;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
