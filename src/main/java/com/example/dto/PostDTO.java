package com.example.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Builder
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

    @Builder
    public PostDTO(@NotNull @Size(max = 100) String postTitle, @NotNull @Size(max = 250) String postDescription, @NotNull String postContent) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postContent = postContent;
    }
}
