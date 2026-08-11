package com.socialmedia.contentcalendar.repository;

import com.socialmedia.contentcalendar.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrPlatformContainingIgnoreCase(
            String title,
            String content,
            String platform
    );

    List<Post> findByStatusIgnoreCase(String status);

    long countByStatusIgnoreCase(String status);
}