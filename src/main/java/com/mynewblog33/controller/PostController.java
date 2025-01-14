package com.mynewblog33.controller;

import com.mynewblog33.payload.PostDto;
import com.mynewblog33.payload.PostResponse;
import com.mynewblog33.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult results){
        if(results.hasErrors()){
            return new ResponseEntity<>(results.getFieldError().getDefaultMessage(),HttpStatus.UNAUTHORIZED);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public PostResponse getAllPosts( @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                      @RequestParam(value = "sortBy",defaultValue = "id", required = false) String sortBy,
                                      @RequestParam(value = "sorDir",defaultValue = "asc",required = false) String sortDir

    ){
        PostResponse post = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return post;
    }
    //http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);


        return ResponseEntity.ok(dto);

    }

//    http://localhost:8080/api/posts/2
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id") long id){
        PostDto postDto1 = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

 //   http://localhost:8080/api/posts/7
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}
