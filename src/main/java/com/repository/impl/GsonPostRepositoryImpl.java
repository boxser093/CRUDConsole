package com.repository.impl;

import com.model.Label;
import com.model.Post;
import com.model.Writer;
import com.repository.PostRepository;

import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {
    public Post getById(Long aLong) throws Exception {
        return null;
    }

    public boolean delete(Long aLong) throws Exception {
        return false;
    }

    public boolean update(Long aLong) throws Exception {
        return true;
    }

    @Override
    public boolean save(Post post) throws Exception {
        return false;
    }

    public Post[] getAll() throws Exception {
        return null;
    }

    public Long getLastId() throws Exception {
        return null;
    }


    public boolean isContainLabel(Label label) throws Exception {
        return false;
    }

    public boolean isContainWriter(Writer writer) throws Exception {
        return false;
    }

    public void checkEdit(Long id) throws Exception {

    }

    public void checkLabel(Long id) throws Exception {

    }

    public void checkWriter(Long id) throws Exception {

    }
}
