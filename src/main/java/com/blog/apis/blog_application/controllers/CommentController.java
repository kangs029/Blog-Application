package com.blog.apis.blog_application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.apis.blog_application.payloads.ApiResponse;
import com.blog.apis.blog_application.payloads.CommentDTO;
import com.blog.apis.blog_application.services.CommentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
        @RequestBody CommentDTO commentDto,
        @PathVariable Integer postId) {
        
        CommentDTO createdComment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(createdComment,HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
       @PathVariable Integer commentId) {
        
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment deleted successfully !",true),HttpStatus.OK);
    }
    

}
