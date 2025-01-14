package com.mynewblog33.service;

import com.mynewblog33.payload.CommentDto;

import java.util.List;

public interface CommentService  {
    CommentDto createComment(long posId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId,long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    void deleteComment(long postId, long commentId);
}
