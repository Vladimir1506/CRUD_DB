package com.vladimir1506.crud_db.controller;

import com.vladimir1506.crud_db.model.Post;
import com.vladimir1506.crud_db.model.Region;
import com.vladimir1506.crud_db.model.Role;
import com.vladimir1506.crud_db.model.User;
import com.vladimir1506.crud_db.repository.UserRepository;


import java.util.List;

public class UserController {
    private UserRepository userRepository;


    public User createUser(String firstName, String lastName, List<Post> posts, Region region, Role role) {
        User user = new User(null, firstName, lastName, posts, region, role);

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    public User updateUser(Long id, String firstName, String lastName, List<Post> posts, Region region, Role role) {
        User updatedUser = new User(id, firstName, lastName, posts, region, role);
        return userRepository.update(updatedUser);
    }
}
