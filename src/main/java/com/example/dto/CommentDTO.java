package com.example.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CommentDTO {

    private long commentId;

    @NotNull
    private String commentText;

    private PostDTO postDTO;

    @Builder
    public CommentDTO(@NotNull String commentText) {
        this.commentText = commentText;
    }
}
