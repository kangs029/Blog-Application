package com.blog.apis.blog_application.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;

    @NotEmpty
    @Size(min=4,message="User name must be min of 4 characters !!")
    private String name;

    @Email(message = "Email address is not valid !!")
    private String email;

    @NotEmpty
    @JsonIgnoreProperties
    @Size(min=3,max = 10,message = "Password must be minimum of 3 chars and max of 10 chars !!")
    private String password;

    @NotEmpty
    private String about;

    private Set<RoleDTO> roles =new HashSet<>();

}
