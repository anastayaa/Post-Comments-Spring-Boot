package com.example.controller;

import java.util.List;

import com.example.advice.PostNotFoundAdvice;
import com.example.dto.PostDTO;
import com.example.exception.PostNotFoundException;
import com.example.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/posts")
public class PostController {

	private static final Logger logger = LogManager.getLogger(PostController.class);
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public List<PostDTO> getPosts(){
		logger.info("Entering PostController.getAllPosts");
		List<PostDTO> posts= postService.getAllPosts();
		logger.info("Exiting PostController.getAllPosts");
		return posts;
	}

	@GetMapping("/{id}")
	public PostDTO getPost(@PathVariable Long id){
		logger.info("Entering PostController.getPost");
		PostDTO post= postService.getOnePost(id);
		logger.info("Exiting PostController.getPost");
		return post;
	}
	
	@PostMapping
	public PostDTO addPost(@Valid @RequestBody PostDTO newPost) {
		logger.info("Entering PostController.addPost");
		PostDTO post= postService.addPost(newPost);
		logger.info("Exiting PostController.addPost");
		return post;
	}

	@PutMapping("/{id}")
	public PostDTO updatePost(@Valid @RequestBody PostDTO newPost, @PathVariable Long id){
		logger.info("Entering PostController.updatePost");
		PostDTO post= postService.updatePost(newPost, id);
		logger.info("Exiting PostController.updatePost");
		return post;
	}

	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable Long id){
		logger.info("Entering PostController.deletePost");
		postService.deletePost(id);
		logger.info("Exiting PostController.deletePost");
	}
}
