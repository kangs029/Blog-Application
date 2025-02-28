package com.blog.apis.blog_application.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.apis.blog_application.entities.Category;
import com.blog.apis.blog_application.exceptions.ResourceNotFoundException;
import com.blog.apis.blog_application.payloads.CategoryDTO;
import com.blog.apis.blog_application.repositories.CategoryRepo;
import com.blog.apis.blog_application.services.CategoryService;

@Service
public class CategoryServiceImlp implements CategoryService{
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category cat=this.modelMapper.map(categoryDTO,Category.class);
        Category createdCat=this.categoryRepo.save(cat);
        return this.modelMapper.map(createdCat,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
       Category cat=this.categoryRepo.findById(categoryId)
                        .orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
        cat.setCategoryTitle(categoryDTO.getCategoryTitle());
        cat.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category updatedCat=this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat,CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId)
                         .orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));

        this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId)
                         .orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
        return this.modelMapper.map(cat,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getCategories() {

        List<Category> categories=this.categoryRepo.findAll();        
        return categories.stream()
                         .map(cat->this.modelMapper.map(cat,CategoryDTO.class))
                         .collect(Collectors.toList());
    }
}
