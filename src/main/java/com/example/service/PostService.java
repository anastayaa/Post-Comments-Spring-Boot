package com.example.service;

import java.util.List;

import com.example.dto.PostDTO;
import com.example.mapper.PostMapper;
import com.example.exception.PostNotFoundException;
import com.example.model.Post;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dao.PostRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class PostService {

    private static final Logger logger = LogManager.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public List<PostDTO> getAllPosts() {
        logger.info("Entering PostService.getAllPosts");
        List<PostDTO> posts = postMapper.toPostDTOs(postRepository.findAll());
        logger.info("Number of posts " + posts.size());
        logger.info("Exiting PostService.getAllPosts");
        return posts;
    }

    public PostDTO getOnePost(Long id) {
        logger.info("Entering PostService.getOnePost {}", id);
        try {
            Post postEntity = postRepository.getOne(id);
            logger.info("Exiting PostService.getOnePost {}", id);
            return postMapper.toPostDTO(postEntity);
        }
        catch (EntityNotFoundException e) {
            throw new PostNotFoundException("Could not find post " + id);
        }
    }

    public PostDTO addPost(PostDTO newpost) {
        logger.info("Entering PostService.addPost");
        logger.info("Adding one Post");
        PostDTO post = postMapper.toPostDTO(
                postRepository.saveAndFlush(
                        postMapper.toPost(newpost)
                )
        );
        logger.info("Post id: " + post.getPostId());
        logger.info("Exiting PostService.addPost");
        return post;
    }

    public PostDTO updatePost(PostDTO newPost, Long id) {
        logger.info("Entering PostService.updatePost {}", id);
        try{
            Post postEntity = postRepository.getOne(id);
            PostDTO post = postMapper.toPostDTO(postEntity);
            post.setPostTitle(newPost.getPostTitle());
            post.setPostDescription(newPost.getPostDescription());
            post.setPostContent(newPost.getPostContent());
            postRepository.save(postMapper.toPost(post));
            logger.info("Exiting PostService.updatePost");
            return post;
        }
        catch (EntityNotFoundException e){
            throw new PostNotFoundException("Could not find post " + id);
        }
    }

    public void deletePost(Long id) {
        logger.info("Entering PostService.deletePost {}", id);
        try{
            Post postEntity = postRepository.getOne(id);
            postRepository.delete(postEntity);
            logger.info("Exiting PostService.deletePost");
        }
        catch (EntityNotFoundException e){
            throw new PostNotFoundException("Could not find post " + id);
        }
    }
}
