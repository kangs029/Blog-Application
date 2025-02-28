package com.blog.apis.blog_application.services;

import com.blog.apis.blog_application.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO,Integer postId);
    void deleteComment(Integer commentId);
}
