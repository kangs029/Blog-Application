package com.blog.apis.blog_application.services;

import java.util.List;

import com.blog.apis.blog_application.payloads.CategoryDTO;


public interface CategoryService {
    //create
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //update
    CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer categoryId);

    //delete
    public void deleteCategory(Integer categoryId);

    //get 
    CategoryDTO getCategoryById(Integer categoryId);

    //get ALL
    List<CategoryDTO> getCategories();
}
