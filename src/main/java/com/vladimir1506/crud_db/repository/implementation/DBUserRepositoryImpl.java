package com.vladimir1506.crud_db.repository.implementation;

import com.vladimir1506.crud_db.model.Post;
import com.vladimir1506.crud_db.model.Region;
import com.vladimir1506.crud_db.model.Role;
import com.vladimir1506.crud_db.model.User;
import com.vladimir1506.crud_db.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class DBUserRepositoryImpl implements UserRepository {
    private final String GETALL = "select u.id,u.firstname,u.lastname,r.id,r.name,u.role from users u " +
            "left join regions r on u.id=r.id " +
            "order by u.id asc";
    private final String SAVEUSER = "insert into users values (%d,'%s','%s',%d,'%s')";
    private final String SAVEPOST = "insert into user_post values (%d,%d)";
    private final String UPDATEUSER = "update users set " +
            "firstname='%s'," +
            "lastname='%s'," +
            "region_id=%d," +
            "role='%s' " +
            "where id=%d";
    private final String DELETE = "delete from users where id=%d";
    private final String GETPOSTS = "select user_id,post_id from user_post up " +
            "left join users u on up.user_id=u.id " +
            "left join posts p on up.post_id=p.id " +
            "where up.user_id=%d";

    @Override
    public List<User> getAll() {
        List<Post> posts = new ArrayList<>();
        List<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = Connect.getStatement(GETALL).executeQuery();
            while (resultSet.next()) {
                Long id = (long) resultSet.getInt("u.id");
                String firstname = resultSet.getString("u.firstname");
                String lastname = resultSet.getString("u.lastname");
                Long regionId = resultSet.getLong("r.id");
                String regionName = resultSet.getString("r.name");
                Region region = new Region(regionId, regionName);
                Role role = Role.valueOf(resultSet.getString("u.role"));
                User user = new User(id, firstname, lastname, posts, region, role);
                users.add(user);
            }
            for (User user : users) {
                user.setPosts(getPostsByUserId(user.getId()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User user) {
        try {
            user.setId(generateID(getAll()));
            String saveQuery = String.format(SAVEUSER,
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRegion().getId(),
                    user.getRole().toString());
            Connect.getStatement(saveQuery).execute();
            addposts(user);
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
        updateQuery = String.format(UPDATEUSER,
                user.getFirstName(),
                user.getLastName(),
                user.getRegion().getId(),
                user.getRole().toString(),
                user.getId());
        try {
            Connect.getStatement(updateQuery).execute();
            addposts(user);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
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

    private Long generateID(List<User> list) {
        return list.stream().map(User::getId).max(Long::compare).orElse(0L) + 1;
    }

    private List<Post> getPostsByUserId(Long userId) {
        List<Post> posts = new ArrayList<>();
        String getPostsByUserIdQuery = String.format(
                GETPOSTS, userId);
        try {
            ResultSet resultSet = Connect.getStatement(getPostsByUserIdQuery).executeQuery();
            while (resultSet.next()) {
                Long postId = resultSet.getLong("post_id");
                posts.add(new DBPostRepositoryImpl().getById(postId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    private void addposts(User user) {
        List<Post> posts = user.getPosts();
        if (!posts.isEmpty()) {
            try {
                for (Post post :
                        posts) {
                    String savePosts = String.format(SAVEPOST, user.getId(), post.getId());
                    Connect.getStatement(savePosts).execute();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
