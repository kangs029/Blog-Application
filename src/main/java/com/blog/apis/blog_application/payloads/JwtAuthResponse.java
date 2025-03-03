package com.blog.apis.blog_application.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
    private UserDTO user;
}
