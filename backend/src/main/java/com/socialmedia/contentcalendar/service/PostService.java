package com.socialmedia.contentcalendar.service;

import com.socialmedia.contentcalendar.model.Post;
import com.socialmedia.contentcalendar.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // CREATE
    public Post createPost(Post post) {

        post.setStatus("DRAFT");

        return postRepository.save(post);
    }

    // VIEW ALL
    public List<Post> getAllPosts() {

        return postRepository.findAll();
    }

    // VIEW ONE
    public Post getPostById(Long id) {

        return postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Post not found with id: " + id)
                );
    }

    // UPDATE
    public Post updatePost(Long id, Post updatedPost) {

        Post existingPost = getPostById(id);

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setPlatform(updatedPost.getPlatform());
        existingPost.setScheduledDate(updatedPost.getScheduledDate());
        existingPost.setScheduledTime(updatedPost.getScheduledTime());

        return postRepository.save(existingPost);
    }

    // SEARCH
    public List<Post> searchPosts(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPosts();
        }

        return postRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrPlatformContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                );
    }

    // STATUS FILTER
    public List<Post> getPostsByStatus(String status) {

        return postRepository.findByStatusIgnoreCase(status);
    }

    // STATUS UPDATE
    public Post updateStatus(Long id, String newStatus, String role) {

        Post post = getPostById(id);

        String currentStatus = post.getStatus().toUpperCase();
        newStatus = newStatus.toUpperCase();
        role = role.toUpperCase();

        if (!isValidTransition(currentStatus, newStatus)) {
            throw new RuntimeException(
                    "Invalid status transition: "
                            + currentStatus + " -> " + newStatus
            );
        }

        if (!isAuthorized(role, currentStatus, newStatus)) {
            throw new RuntimeException(
                    "Role " + role
                            + " is not authorized for this status transition"
            );
        }

        post.setStatus(newStatus);

        return postRepository.save(post);
    }

    private boolean isValidTransition(
            String currentStatus,
            String newStatus) {

        if (currentStatus.equals("DRAFT")
                && newStatus.equals("IN_REVIEW")) {
            return true;
        }

        if (currentStatus.equals("IN_REVIEW")
                && (newStatus.equals("APPROVED")
                || newStatus.equals("REJECTED"))) {
            return true;
        }

        if (currentStatus.equals("APPROVED")
                && newStatus.equals("PUBLISHED")) {
            return true;
        }

        return false;
    }

    private boolean isAuthorized(
            String role,
            String currentStatus,
            String newStatus) {

        if (role.equals("ADMIN")) {
            return true;
        }

        if (role.equals("CREATOR")
                && currentStatus.equals("DRAFT")
                && newStatus.equals("IN_REVIEW")) {
            return true;
        }

        if (role.equals("REVIEWER")
                && currentStatus.equals("IN_REVIEW")
                && (newStatus.equals("APPROVED")
                || newStatus.equals("REJECTED"))) {
            return true;
        }

        if (role.equals("PUBLISHER")
                && currentStatus.equals("APPROVED")
                && newStatus.equals("PUBLISHED")) {
            return true;
        }

        return false;
    }

    // DASHBOARD
    public long getTotalPosts() {

        return postRepository.count();
    }

    public long getDraftCount() {

        return postRepository.countByStatusIgnoreCase("DRAFT");
    }

    public long getInReviewCount() {

        return postRepository.countByStatusIgnoreCase("IN_REVIEW");
    }

    public long getApprovedCount() {

        return postRepository.countByStatusIgnoreCase("APPROVED");
    }

    public long getRejectedCount() {

        return postRepository.countByStatusIgnoreCase("REJECTED");
    }

    public long getPublishedCount() {

        return postRepository.countByStatusIgnoreCase("PUBLISHED");
    }
}