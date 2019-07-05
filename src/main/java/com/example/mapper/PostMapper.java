package com.example.mapper;

import com.example.dto.PostDTO;
import com.example.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {


    @Mapping(target="postId", source="entity.id")
    @Mapping(target="postTitle", source="entity.title")
    @Mapping(target="postDescription", source="entity.description")
    @Mapping(target="postContent", source="entity.content")
    PostDTO toPostDTO(Post entity);
    List<PostDTO> toPostDTOs(List<Post> entity);


    @Mapping(target="id", source="dto.postId")
    @Mapping(target="title", source="dto.postTitle")
    @Mapping(target="description", source="dto.postDescription")
    @Mapping(target="content", source="dto.postContent")
    Post toPost(PostDTO dto);
    List<Post> toPosts(List<PostDTO> dto);

}
