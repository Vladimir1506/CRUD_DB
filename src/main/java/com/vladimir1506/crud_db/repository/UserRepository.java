package com.vladimir1506.crud_db.repository;

import com.vladimir1506.crud_db.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<User, Long> {

    List<User> getAll();

    User save(User user);

    User getById(Long id);

    User update(User user);

    void delete(Long id);
}
