package com.vladimir1506.crud_db.controller;

import com.vladimir1506.crud_db.model.Post;
import com.vladimir1506.crud_db.repository.PostRepository;

import java.util.Date;
import java.util.List;

public class PostController {
    private PostRepository postRepository;

    public Post createPost(String content) {
        Post post = new Post(null, content);
        return postRepository.save(post);
    }

    public List<Post> getAll() {
        return postRepository.getAll();

    }

    public void deletePost(Long id) {
        postRepository.delete(id);
    }

    public Post getPostById(Long id) {
        return postRepository.getById(id);
    }

    public Post updatePost(Long id, String name) {
        Post updatedPost = new Post(id, name);
        updatedPost.setUpdated(new Date());
        return postRepository.update(updatedPost);
    }
}
