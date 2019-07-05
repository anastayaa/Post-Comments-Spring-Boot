package com.example.controller;

import com.example.dto.CommentDTO;
import com.example.exception.PostNotFoundException;
import com.example.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/comments")
public class CommentController {

    private static final Logger logger = LogManager.getLogger(PostController.class);
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDTO> getComments(){
        logger.info("Entering CommentController.getAllComments");
        List<CommentDTO> comments= commentService.getAllComments();
        logger.info("Exit CommentController.getAllComments");
        return comments;
    }

    @GetMapping("/{id}")
    public CommentDTO getComment(@PathVariable Long id){
        logger.info("Entering CommentController.getComment");
        CommentDTO comment= commentService.getOneComment(id);
        logger.info("Exiting CommentController.getComment");
        return comment;
    }

    @PostMapping("/{postId}")
    public CommentDTO addComment(@Valid @RequestBody CommentDTO newComment, @PathVariable Long postId){
        logger.info("Entering CommentController.addComment");
        CommentDTO comment= commentService.addComment(newComment, postId);
        logger.info("Exiting CommentController.addComment");
        return comment;
    }

    @PutMapping("/{id}")
    public CommentDTO updateComment(@Valid @RequestBody CommentDTO newComment, @PathVariable Long id){
        logger.info("Entering CommentController.updateComment");
        CommentDTO comment= commentService.updateComment(newComment, id);
        logger.info("Exiting CommentController.updateComment");
        return comment;
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
        logger.info("Entering CommentController.deleteComment");
        commentService.deleteComment(id);
        logger.info("Exiting CommentController.deleteComment");
    }
}
