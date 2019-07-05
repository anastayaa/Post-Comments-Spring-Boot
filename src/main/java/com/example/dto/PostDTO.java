package com.example.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PostDTO implements Serializable {

    private long postId;

    @NotNull
    @Size(max = 100)
    private String postTitle;

    @NotNull
    @Size(max = 250)
    private String postDescription;

    @NotNull
    private String postContent;

    public PostDTO() {
    }

    public PostDTO(String postTitle, String postDescription, String postContent) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postContent = postContent;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
}
