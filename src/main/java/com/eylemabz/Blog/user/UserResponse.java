package com.eylemabz.Blog.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    @NotBlank
    private String userName;
    @NotBlank
    @Email
    private String email;

}
