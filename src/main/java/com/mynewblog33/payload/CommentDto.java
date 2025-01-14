package com.mynewblog33.payload;

import com.mynewblog33.entity.Post;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min = 4,message = "name should be at least 4 character")
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 5,message = "body should be at least 5 character")
    private String body;
    //private Post post;
}
