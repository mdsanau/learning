package com.mynewblog33.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty(message = "is mandatory")
    @Size(min = 2, message = "Post title should at least 2 character")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "Post description should have at least 10 character")
    private String description;
    @NotEmpty
    @Size(min = 10, message = "Post content should be at least 10 characters")
    private String content;
}
