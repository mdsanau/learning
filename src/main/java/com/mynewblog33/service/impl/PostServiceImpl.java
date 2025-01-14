package com.mynewblog33.service.impl;

import com.mynewblog33.entity.Post;
import com.mynewblog33.exception.ResourceNotFoundException;
import com.mynewblog33.payload.PostDto;
import com.mynewblog33.payload.PostResponse;
import com.mynewblog33.repository.PostRepository;
import com.mynewblog33.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(ModelMapper modelMapper,PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        PostDto newPostDto = mapToDto(newPost);
        return newPostDto;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> content = posts.getContent();




       // List<Post> posts = postRepository.findAll();
        List<PostDto> dtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();

        postResponse.setContent(dtos);

        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
//                orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deleteById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
//        Post post=new Post();
//        post.setDescription(postDto.getDescription());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());

        return post;
    }
    PostDto mapToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());
        return postDto;
    }

}
