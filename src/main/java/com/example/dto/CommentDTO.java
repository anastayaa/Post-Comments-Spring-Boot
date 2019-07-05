package com.example.dto;

import javax.validation.constraints.NotNull;

public class CommentDTO {

    private long commentId;

    @NotNull
    private String commentText;

    private PostDTO postDTO;

    public CommentDTO() {
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }
}
