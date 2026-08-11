package com.socialmedia.contentcalendar.controller;

import com.socialmedia.contentcalendar.model.Post;
import com.socialmedia.contentcalendar.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {

        Post createdPost = postService.createPost(post);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPost);
    }
}