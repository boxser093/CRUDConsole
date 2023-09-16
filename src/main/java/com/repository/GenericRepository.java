package com.repository;

import com.model.Writer;

import java.util.List;

public interface GenericRepository<T, ID> {
    T getById(ID id) throws Exception;

    boolean delete(ID id) throws Exception;

    boolean update(ID id) throws Exception;

    boolean save(T t) throws Exception;

    T[] getAll() throws Exception;

}
