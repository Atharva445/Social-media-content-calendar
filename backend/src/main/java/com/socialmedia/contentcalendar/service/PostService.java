package com.socialmedia.contentcalendar.service;

import com.socialmedia.contentcalendar.model.Post;
import com.socialmedia.contentcalendar.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {

        if (post.getStatus() == null || post.getStatus().isBlank()) {
            post.setStatus("DRAFT");
        }

        return postRepository.save(post);
    }
}