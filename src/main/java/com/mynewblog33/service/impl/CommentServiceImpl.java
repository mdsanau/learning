package com.mynewblog33.service.impl;


import com.mynewblog33.entity.Comment;
import com.mynewblog33.entity.Post;
import com.mynewblog33.exception.BlogApiException;
import com.mynewblog33.exception.ResourceNotFoundException;
import com.mynewblog33.payload.CommentDto;
import com.mynewblog33.repository.CommentRepository;
import com.mynewblog33.repository.PostRepository;
import com.mynewblog33.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository,CommentRepository commentRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository=commentRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        CommentDto dto=mapToDto(newComment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

       // Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException (HttpStatus.BAD_REQUEST,"comment does not belongs with this post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if (comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Post not matching with the comment");
        }
//        comment.setId(commentId);
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
//        comment.setPost(commentDto.getPost());
        Comment comment1 = commentRepository.save(comment);
       return  mapToDto(comment1);

    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
       Comment comment= commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        if(comment.getPost().getId()!=post.getId()) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        commentRepository.delete(comment);

    }

    private CommentDto mapToDto(Comment newComment) {
        CommentDto commentDto = modelMapper.map(newComment, CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(newComment.getId());
//        commentDto.setName(newComment.getName());
//        commentDto.setEmail(newComment.getEmail());
//        commentDto.setBody(newComment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment= new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
