package com.vladimir1506.crud_db.repository;

import com.vladimir1506.crud_db.model.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Long> {
    List<Post> getAll();

    Post getById(Long id);

    Post save(Post post);

    Post update(Post post);

    void delete(Long id);
}
