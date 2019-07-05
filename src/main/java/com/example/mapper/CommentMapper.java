package com.example.mapper;

import com.example.dto.CommentDTO;
import com.example.dto.PostDTO;
import com.example.model.Comment;
import com.example.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target="commentId", source="entity.id")
    @Mapping(target="commentText", source="entity.text")
    @Mapping(target="postDTO", source="entity.post")
    CommentDTO toCommentDTO(Comment entity);
    List<CommentDTO> toCommentDTOs(List<Comment> entity);

    @Mapping(target="id", source="dto.commentId")
    @Mapping(target="text", source="dto.commentText")
    @Mapping(target="post", source="dto.postDTO")
    Comment toComment(CommentDTO dto);
    List<Comment> toComments(List<CommentDTO> dto);

    @Mapping(target="postId", source="entity.id")
    @Mapping(target="postTitle", source="entity.title")
    @Mapping(target="postDescription", source="entity.description")
    @Mapping(target="postContent", source="entity.content")
    PostDTO toPostDTO(Post entity);

    @Mapping(target="id", source="dto.postId")
    @Mapping(target="title", source="dto.postTitle")
    @Mapping(target="description", source="dto.postDescription")
    @Mapping(target="content", source="dto.postContent")
    Post toPost(PostDTO dto);
}
