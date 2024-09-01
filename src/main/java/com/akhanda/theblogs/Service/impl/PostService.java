package com.akhanda.theblogs.Service.impl;

import com.akhanda.theblogs.Dto.PostDto;
import com.akhanda.theblogs.Exception.ResourceNotFoundException;
import com.akhanda.theblogs.Model.Post;
import com.akhanda.theblogs.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements com.akhanda.theblogs.Service.PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert DTO to entity
        Post post=mapToEntity(postDto);

        Post newpost = postRepository.save(post);

        //convert entity into DTO
        PostDto postResponse = mapToDTO(newpost);

        return postResponse;
    }



   @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize) {

        //create pagable instance
        Pageable pageable= (Pageable) PageRequest.of(pageNo, pageSize);
        Page<Post> posts=postRepository.findAll((org.springframework.data.domain.Pageable) pageable);

        // get context for page object
        List<Post> listOfPosts = posts.getContent();
        return listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

    }


    @Override
    public PostDto getPostById(Long id) {
        Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDTO(post);

    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id" , id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContext(postDto.getContext());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost) ;
    }

    @Override
    public void deletePostById(long id) {

        Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);

    }

    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContext(post.getContext());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContext(postDto.getContext());
        return post;
    }
}
