package com.socialmedia.contentcalendar.controller;

import com.socialmedia.contentcalendar.model.Post;
import com.socialmedia.contentcalendar.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Post> createPost(
            @Valid @RequestBody Post post) {

        Post createdPost = postService.createPost(post);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPost);
    }

    // VIEW ALL
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {

        return ResponseEntity.ok(
                postService.getAllPosts()
        );
    }

    // VIEW ONE
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                postService.getPostById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody Post post) {

        return ResponseEntity.ok(
                postService.updatePost(id, post)
        );
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(
            @RequestParam(required = false) String keyword) {

        return ResponseEntity.ok(
                postService.searchPosts(keyword)
        );
    }

    // FILTER BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Post>> getPostsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                postService.getPostsByStatus(status)
        );
    }

    // ROLE-BASED STATUS UPDATE
    @PatchMapping("/{id}/status")
    public ResponseEntity<Post> updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestHeader(value = "X-User-Role", defaultValue = "CREATOR")
            String role) {

        return ResponseEntity.ok(
                postService.updateStatus(id, status, role)
        );
    }

    // DASHBOARD
    @GetMapping("/dashboard/summary")
    public ResponseEntity<Map<String, Long>> getDashboardSummary() {

        Map<String, Long> summary = new HashMap<>();

        summary.put("total", postService.getTotalPosts());
        summary.put("draft", postService.getDraftCount());
        summary.put("inReview", postService.getInReviewCount());
        summary.put("approved", postService.getApprovedCount());
        summary.put("rejected", postService.getRejectedCount());
        summary.put("published", postService.getPublishedCount());

        return ResponseEntity.ok(summary);
    }
}