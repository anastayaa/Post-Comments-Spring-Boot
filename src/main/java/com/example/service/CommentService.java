package com.example.service;

import com.example.dao.CommentRepository;
import com.example.dao.PostRepository;
import com.example.dto.CommentDTO;
import com.example.dto.PostDTO;
import com.example.exception.CommentNotFoundException;
import com.example.exception.PostNotFoundException;
import com.example.mapper.CommentMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private static final Logger logger = LogManager.getLogger(PostService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    public List<CommentDTO> getAllComments() {
        logger.info("Entering CommentService.getAllComments");
        logger.info("Getting all Comment");
        List<CommentDTO> comments = commentMapper.toCommentDTOs(commentRepository.findAll());
        logger.info("Number of Comments " + comments.size());
        logger.info("Exiting CommentService.getAllComments");
        return comments;
    }

    public CommentDTO getOneComment(long id) {
        logger.info("Entering CommentService.getOneComment");
        logger.info("Getting one Comment");
        CommentDTO comment = commentMapper.toCommentDTO(commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Could not find comment " + id)));
        logger.info("Comment id: " + comment.getCommentId());
        logger.info("Exit CommentService.getOneComment");
        return comment;
    }

    public CommentDTO addComment(CommentDTO newComment, Long postId) {
        logger.info("Entering CommentService.addComment");
        logger.info("Adding one Comment");
        PostDTO post = commentMapper.toPostDTO(postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Could not find post " + postId)));
        newComment.setPostDTO(post);
        CommentDTO comment = commentMapper.toCommentDTO(
                commentRepository.saveAndFlush(
                        commentMapper.toComment(newComment)
                )
        );
        logger.info("Comment id: " + comment.getCommentId());
        logger.info("Exiting PostService.addPost");
        return comment;
    }

    public CommentDTO updateComment(CommentDTO newComment, Long id) {
        logger.info("Entering CommentService.updateComment");
        logger.info("Updating Comment");
        CommentDTO comment = commentMapper.toCommentDTO(
                commentRepository.findById(id)
                        .map(c -> {
                            c.setText(newComment.getCommentText());
                            return commentRepository.saveAndFlush(c);
                        })
                        .orElseThrow(() -> new CommentNotFoundException("Could not find comment " + id))
        );
        logger.info("Comment id: " + comment.getCommentId());
        logger.info("Exit CommentService.updateComment");
        return comment;
    }

    public void deleteComment(Long id) {
        logger.info("Entering CommentService.deleteComment");
        logger.info("Deleting Comment");
        commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new CommentNotFoundException("Could not find comment " + id));
        logger.info("Comment id; " + id);
        logger.info("Exiting CommentService.updateComment");
    }
}
