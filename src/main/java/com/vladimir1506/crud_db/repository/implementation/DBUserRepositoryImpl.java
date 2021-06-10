package com.vladimir1506.crud_db.repository.implementation;

import com.vladimir1506.crud_db.model.Post;
import com.vladimir1506.crud_db.model.Region;
import com.vladimir1506.crud_db.model.Role;
import com.vladimir1506.crud_db.model.User;
import com.vladimir1506.crud_db.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBUserRepositoryImpl implements UserRepository {

    @Override
    public List<User> getAll() {
        List<Post> posts;
        List<User> users = new ArrayList<>();
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        try {
            ResultSet resultSet = statement.executeQuery(
                    "select u.id,u.firstname,u.lastname,r.id,r.name,u.role from users u " +
                            "left join regions r on u.id=r.id " +
                            "order by u.id asc");
            while (resultSet.next()) {
                Long id = (long) resultSet.getInt("u.id");
                String firstname = resultSet.getString("u.firstname");
                String lastname = resultSet.getString("u.lastname");
                posts = getPostsByUserId(id);
                Long regionId = resultSet.getLong("r.id");
                String regionName = resultSet.getString("r.name");
                Region region = new Region(regionId, regionName);
                Role role = Role.valueOf(resultSet.getString("u.role"));
                User user = new User(id, firstname, lastname, posts, region, role);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User user) {
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        try {
            user.setId(generateID(getAll()));
            String saveQuery = String.format("insert into users values (%d,'%s','%s',%d,'%s')",
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRegion().getId(),
                    user.getRole().toString());
            List<Post> posts = user.getPosts();
            statement.execute(saveQuery);
            for (Post post :
                    posts) {
                String savePosts = String.format("insert into user_post values (%d,%d)", user.getId(), post.getId());
                statement.execute(savePosts);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public User getById(Long id) {
        List<User> users;
        User wantedUser = null;
        users = getAll();
        if (users != null) {
            for (User user : users
            ) {
                if (user.getId().equals(id)) {
                    wantedUser = user;
                }
            }
        }
        return wantedUser;
    }

    @Override
    public User update(User user) {
        String updateQuery;
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        updateQuery = String.format(
                "update users set " +
                        "firstname='%s'," +
                        "lastname='%s'," +
                        "posts='%s'," +
                        "region='%s'," +
                        "role='%s' " +
                        "where id=%d",
                user.getFirstName(),
                user.getLastName(),
                this.getPostIds(user),
                user.getRegion().getName(),
                user.getRole().toString(),
                user.getId());
        try {
            statement.execute(updateQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        String deleteQuery;
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        deleteQuery = String.format("delete from users where id=%d", id);
        try {
            statement.execute(deleteQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Long generateID(List<User> list) {
        return list.stream().map(User::getId).max(Long::compare).orElse(0L) + 1;
    }

    private String getPostIds(User user) {
        if (!user.getPosts().isEmpty()) {
            List<Long> posts = user.getPosts().stream().map(Post::getId).collect(Collectors.toList());
            return posts.toString().replaceAll("[ \\[\\]]", "");
        } else return null;
    }

    public List<Post> getPostsByUserId(Long userId) {
        List<Post> posts = new ArrayList<>();
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        String getPostsByUserIdQuery = String.format(
                "select user_id,post_id from user_post up " +
                        "left join users u on up.user_id=u.id " +
                        "left join posts p on up.post_id=p.id " +
                        "where up.user_id=%d", userId);
        try {
            ResultSet resultSet = statement.executeQuery(getPostsByUserIdQuery);
            while (resultSet.next()) {
                Long postId = resultSet.getLong("post_id");
                posts.add(new DBPostRepositoryImpl().getById(postId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }
}
