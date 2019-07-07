package com.example.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @NotNull
    @Size(max = 250)
    private String description;

    @NotNull
    @Lob
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Builder
    public Post(@NotNull @Size(max = 100) String title, @NotNull @Size(max = 250) String description, @NotNull String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }
}
