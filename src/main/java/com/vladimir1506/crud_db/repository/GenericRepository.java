package com.vladimir1506.crud_db.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T, ID> {

    List<T> getAll() throws SQLException, ClassNotFoundException;

    T getById(ID id);

    T save(T t);

    T update(T t);

    void delete(ID id);
}
