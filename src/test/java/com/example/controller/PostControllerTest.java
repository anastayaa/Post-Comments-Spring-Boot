package com.example.controller;

import com.example.dto.PostDTO;
import com.example.exception.PostNotFoundException;
import com.example.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PostControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private PostController postController;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private PostService postService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void getAllPostsTest() throws Exception {
        List<PostDTO> list=new ArrayList<>();
        PostDTO post=new PostDTO();
        post.setPostTitle("First post");
        post.setPostDescription("My first post");
        post.setPostContent("My first content");
        list.add(post);

        when(postService.getAllPosts()).thenReturn(list);
        mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].postTitle", is(post.getPostTitle())))
                .andExpect(jsonPath("$[0].postDescription", is(post.getPostDescription())))
                .andExpect(jsonPath("$[0].PostContent", is(post.getPostContent())))
                .andDo(print());
        verify(postService, times(1)).getAllPosts();
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void getOnePostTest() throws Exception {
        long id =1L;
        PostDTO post=new PostDTO();
        post.setPostTitle("First post");
        post.setPostDescription("My first post");
        post.setPostContent("My first content");

        when(postService.getOnePost(id)).thenReturn(post);
        mockMvc.perform(get("/posts/{}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].postTitle", is(post.getPostTitle())))
                .andExpect(jsonPath("$[0].postDescription", is(post.getPostDescription())))
                .andExpect(jsonPath("$[0].PostContent", is(post.getPostContent())))
                .andDo(print());
        verify(postService, times(1)).getOnePost(id);
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void getOnePostNotFoundTest() throws Exception {
        long id =1L;
        when(postService.getOnePost(id)).thenThrow(new PostNotFoundException("Could not find Post "+id));
        mockMvc.perform(get("/posts/{}", id)).andExpect(status().isNotFound());
        verify(postService, times(1)).getOnePost(id);
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void addPostTest() throws Exception {
        PostDTO post=new PostDTO();
        post.setPostTitle("First post");
        post.setPostDescription("My first post");
        post.setPostContent("My first content");

        when(postService.addPost(post)).thenReturn(post);
        mockMvc.perform(post("/posts/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("postTitle").exists())
                .andExpect(jsonPath("$.postDescription").exists())
                .andExpect(jsonPath("$.postContent").exists())
                .andExpect(jsonPath("$.postTitle").value("First post"))
                .andExpect(jsonPath("$.postDescription").value("My first post"))
                .andExpect(jsonPath("$.postContent").value("My first content"))
                .andDo(print())
                .andReturn();
        verify(postService, times(1)).addPost(post);
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void addPostNotValidTest() throws Exception {
        PostDTO post=new PostDTO();
        mockMvc.perform(post("/posts/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
                .andExpect(jsonPath("$.fieldErrors[*].path", containsInAnyOrder("title", "description", "content")));
        verifyZeroInteractions(postService);
    }
    @Test
    public void updatePostTest() throws Exception {
        long id=1L;
        PostDTO post=new PostDTO();
        post.setPostTitle("First post");
        post.setPostDescription("My first post");
        post.setPostContent("My first content");

        when(postService.updatePost(post, id)).thenReturn(post);
        mockMvc.perform(put("/posts/{}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("postTitle").exists())
                .andExpect(jsonPath("$.postDescription").exists())
                .andExpect(jsonPath("$.postContent").exists())
                .andExpect(jsonPath("$.postTitle").value("First post"))
                .andExpect(jsonPath("$.postDescription").value("My first post"))
                .andExpect(jsonPath("$.postContent").value("My first content"))
                .andDo(print())
                .andReturn();
        verify(postService, times(1)).updatePost(post, id);
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void updatePostNotValid() throws Exception {
        long id=1L;
        PostDTO post=new PostDTO();
        mockMvc.perform(put("/posts/", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
                .andExpect(jsonPath("$.fieldErrors[*].path", containsInAnyOrder("title", "description", "content")));
        verifyZeroInteractions(postService);
    }
}
