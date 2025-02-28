package com.blog.apis.blog_application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.apis.blog_application.payloads.ApiResponse;
import com.blog.apis.blog_application.payloads.CategoryDTO;
import com.blog.apis.blog_application.services.CategoryService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //POST - create category
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO catDto= this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(catDto,HttpStatus.CREATED); 
    }
    

    //PUT -update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO>updateCategory(@Valid@PathVariable("categoryId") Integer id, @RequestBody CategoryDTO categoryDTO) {
       CategoryDTO updateCategoryDTO=this.categoryService.updateCategory(categoryDTO, id);
       return ResponseEntity.ok(updateCategoryDTO);
    }

    //DELETE -delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable("categoryId") Integer id) {
       this.categoryService.deleteCategory(id);
       return new ResponseEntity<>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
    }

    //GET - getById
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("categoryId") Integer id){
        CategoryDTO categoryDTO=this.categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
    }
    
 
    //GET - getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        List<CategoryDTO> categoryDTOs =this.categoryService.getCategories();
        return new ResponseEntity<>(categoryDTOs,HttpStatus.OK);
    }
}
