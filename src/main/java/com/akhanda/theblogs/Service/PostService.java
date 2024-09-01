package com.akhanda.theblogs.Service;

import com.akhanda.theblogs.Dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts(int pageNo, int pageSize);

    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
