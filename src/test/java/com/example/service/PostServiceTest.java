package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.example.dao.PostRepository;
import com.example.exception.PostNotFoundException;
import com.example.mapper.PostMapper;
import com.example.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;


    @Test
    public void getAllPostsTest() {
        List<Post> list = new ArrayList<>();
        Post post = new Post();
        post.setTitle("First post");
        post.setDescription("DMy first post");
        post.setContent("My first content");
        list.add(post);

        Mockito.when(postRepository.findAll()).thenReturn(list);

        List<Post> posts = postMapper.toPosts(postService.getAllPosts());

        assertEquals(list.size(), posts.size());
        verify(postRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getOnePostTest() {
        long id = 1;
        when(postRepository.getOne(id))
                .thenReturn(new Post("First post", "My first post", "My first content"));
        Post post = postMapper.toPost(postService.getOnePost((long) 1));
        assertThat(post).isNotNull();
    }

    @Test(expected = PostNotFoundException.class)
    public void getOnePostNotFoundTest(){
        long id = 1;
        when(postRepository.getOne(id)).thenThrow(EntityNotFoundException.class);
        postService.getOnePost(id);
    }

    @Test
    public void addPostTest() {
        long id = 1;
        Post newPost = new Post("First post", "My first post", "My first content");
        newPost.setId(id);
        when(postRepository.saveAndFlush(any(Post.class))).then(returnsFirstArg());
        Post post = postMapper.toPost(postService.addPost(postMapper.toPostDTO(newPost)));
        assertThat(post.getTitle()).isNotNull();
    }

    @Test
    public void updatePost() {
        long id = 1;
        Post newPost = new Post("First post", "My first post", "My first content");
        newPost.setId(id);
        when(postRepository.getOne(id)).thenReturn(newPost);
        when(postRepository.save(any(Post.class))).then(returnsFirstArg());
        Post post = postMapper.toPost(postService.updatePost(postMapper.toPostDTO(newPost), id));
        assertEquals(post.getTitle(), newPost.getTitle());
    }

    @Test
    public void deletePost() {
        long id = 1;
        when(postRepository.getOne(id)).
                thenReturn(new Post("First post", "My first post", "My first content"));
        postService.deletePost(id);
        verify(postRepository, times(1)).delete(any(Post.class));
    }
}
