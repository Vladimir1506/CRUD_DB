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
    Connect connect;
    Statement statement;
    ResultSet resultSet;

    public DBUserRepositoryImpl() {
        connect = new Connect();
        statement = connect.getStatement();
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        List<Post> posts;
        try {
            resultSet = statement.executeQuery("select * from users order by id asc");
            while (resultSet.next()) {
                posts = new ArrayList<>();
                Long id = (long) resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                if (!resultSet.getString("posts").equals("null")) {
                    String[] postsArray = resultSet.getString("posts").split(",");
                    for (String postId : postsArray) {
                        posts.add(new DBPostRepositoryImpl().getById(Long.parseLong(postId)));
                    }
                }
                String regionString = resultSet.getString("region");
                Region region = new DBRegionRepositoryImpl().getByName(regionString);
                String roleString = resultSet.getString("role");
                Role role = Role.valueOf(roleString);
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
        try {
            user.setId(generateID(getAll()));
            String saveQuery = String.format("insert into users values (%d,'%s','%s','%s','%s','%s')",
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    this.getPostIds(user),
                    user.getRegion().getName(),
                    user.getRole().toString());
            statement.execute(saveQuery);
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
            System.out.println(user.getPosts());
            List<Long> posts = user.getPosts().stream().map(Post::getId).collect(Collectors.toList());
            return posts.toString().replaceAll("[ \\[\\]]", "");
        } else return null;
    }
}
