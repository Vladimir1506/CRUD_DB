package com.vladimir1506.crud_db.repository.implementation;

import com.vladimir1506.crud_db.model.Post;
import com.vladimir1506.crud_db.repository.PostRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBPostRepositoryImpl implements PostRepository {
    private final String GETALL = "select * from posts order by id asc";
    private final String SAVE = "insert into posts values (%d,'%s','%s','%s')";
    private final String UPDATE = "update posts set content='%s',updated='%s' where id=%d";
    private final String DELETE = "delete from posts where id=%d";

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try {
            ResultSet resultSet = Connect.getStatement(GETALL).executeQuery();
            while (resultSet.next()) {
                Long id = (long) resultSet.getInt("id");
                String content = resultSet.getString("content");
                Date created = resultSet.getTimestamp("created");
                Date updated = resultSet.getTimestamp("updated");
                Post post = new Post(id, content);
                post.setCreated(created);
                post.setUpdated(updated);
                posts.add(post);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post getById(Long id) {
        List<Post> posts;
        Post wantedPost = null;
        posts = getAll();
        if (posts != null) {
            for (Post post : posts
            ) {
                if (post.getId().equals(id)) {
                    wantedPost = post;
                }
            }
        }
        return wantedPost;
    }

    @Override
    public Post save(Post post) {
        try {
            List<Post> posts = getAll();
            post = new Post(generateID(posts), post.getContent());
            String created = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(post.getCreated());
            String updated = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(post.getUpdated());
            String saveQuery = String.format(SAVE, post.getId(), post.getContent(), created, updated);
            Connect.getStatement(saveQuery).execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        String updateQuery;

        post.setUpdated(new Date());
        String updated = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(post.getUpdated());
        updateQuery = String.format(UPDATE, post.getContent(), updated, post.getId());
        try {
            Connect.getStatement(updateQuery).execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public void delete(Long id) {
        String deleteQuery;
        deleteQuery = String.format(DELETE, id);
        try {
            Connect.getStatement(deleteQuery).execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Long generateID(List<Post> list) {
        return list.stream().map(Post::getId).max(Long::compare).orElse(0L) + 1;
    }
}
