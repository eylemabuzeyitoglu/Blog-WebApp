package com.eylemabz.Blog.comment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private Long blogId;
    private Long userId;
    private Long commentId;
    private String comment;

}
