package com.blog.apis.blog_application.services.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.apis.blog_application.entities.Comment;
import com.blog.apis.blog_application.entities.Post;
import com.blog.apis.blog_application.exceptions.ResourceNotFoundException;
import com.blog.apis.blog_application.payloads.CommentDTO;
import com.blog.apis.blog_application.repositories.CommentRepo;
import com.blog.apis.blog_application.repositories.PostRepo;
import com.blog.apis.blog_application.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        Optional<Post> posT= this.postRepo.findById(postId);
        if(posT.isEmpty()){
            throw new ResourceNotFoundException("Post","PostId",postId);
        }
        Post post =posT.get();

        Comment comment = this.modelMapper.map(commentDTO,Comment.class);
        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDTO.class) ;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId)
                        .orElseThrow(()->new ResourceNotFoundException("commentId", "comment", commentId));
        this.commentRepo.delete(comment);
    }

}

