package com.blog.apis.blog_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.apis.blog_application.payloads.ApiResponse;
import com.blog.apis.blog_application.payloads.UserDTO;
import com.blog.apis.blog_application.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //POST - crete user
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto){
       UserDTO createUserDto = this.userService.createUser(userDto);
       return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
    }

    //PUT - update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDto,@PathVariable Integer userId){
       UserDTO updatedUserDto = this.userService.updateUser(userDto,userId);
       return ResponseEntity.ok(updatedUserDto);
    }

    //ADMIN
	// DELETE -delete user
	 @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);
       return new ResponseEntity<>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
    }

    //GET - users get
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
       return ResponseEntity.ok(this.userService.getAllUsers());
    }
    
    //GET - single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId){
       return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
