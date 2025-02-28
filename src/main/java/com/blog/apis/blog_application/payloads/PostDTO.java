package com.blog.apis.blog_application.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.apis.blog_application.entities.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
    //private Integer postId; It will generate automaticaly not madnatory
    private Integer postId;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max=10000)
    private String content;

    private String imageName;
    
   //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date addDate;

    private CategoryDTO category;

    private UserDTO user;

    private Set<Comment> comments=new HashSet<>();

}
