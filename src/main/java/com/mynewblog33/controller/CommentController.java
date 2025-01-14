package com.mynewblog33.controller;

import com.mynewblog33.entity.Post;
import com.mynewblog33.payload.CommentDto;
import com.mynewblog33.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //http://localhost:8081/api/posts/1/comments

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> saveComment(@PathVariable("postId") long postId,@Valid @RequestBody CommentDto commentDto, BindingResult result) {

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentDto commentDto1 = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    //http://localhost:8081/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    //http://localhost:8081/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId) {
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);

    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable("id") long commentId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto commentDto1 = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(commentDto1,HttpStatus.OK);

    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,@PathVariable("id") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
}
