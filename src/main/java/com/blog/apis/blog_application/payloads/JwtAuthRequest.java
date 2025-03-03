package com.blog.apis.blog_application.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}
