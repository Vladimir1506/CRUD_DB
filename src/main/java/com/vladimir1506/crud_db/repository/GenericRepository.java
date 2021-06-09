package com.vladimir1506.crud_db.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    List<T> getAll();

    T getById(ID id);

    T save(T t);

    T update(T t);

    void delete(ID id);
}
