package com.blog.apis.blog_application.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Integer categoryId;

    @NotBlank
    @Size(min=4,message = "Min Size of category tile should be 4")
    private String categoryTitle;

    @NotBlank
    @Size(min=10 ,max=50)
    private String categoryDescription;

}
