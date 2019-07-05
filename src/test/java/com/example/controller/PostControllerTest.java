package com.example.controller;

import com.example.dto.PostDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PostControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private PostController postController;

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

        given(postService.getAllPosts()).willReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());
    }

    @Test
    public void getOnePostTest() throws Exception {
        long id =1L;
        PostDTO post=new PostDTO();
        post.setPostTitle("First post");
        post.setPostDescription("My first post");
        post.setPostContent("My first content");

        given(postService.getOnePost(id)).willReturn(post);
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void addPostTest() throws Exception {
        PostDTO post=new PostDTO();
        post.setPostTitle("First post");
        post.setPostDescription("My first post");
        post.setPostContent("My first content");

        given(postService.addPost(post)).willReturn(post);
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(post))
                .accept(MediaType.APPLICATION_JSON))
                /*
                .andExpect(jsonPath("postTitle").exists())
                .andExpect(jsonPath("$.postDescription").exists())
                .andExpect(jsonPath("$.postContent").exists())
                .andExpect(jsonPath("$.postTitle").value("First post"))
                .andExpect(jsonPath("$.postDescription").value("My first post"))
                .andExpect(jsonPath("$.postContent").value("My first content"))
                */
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
