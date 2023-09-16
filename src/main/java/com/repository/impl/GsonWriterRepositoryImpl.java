package com.repository.impl;

import com.model.Writer;
import com.repository.WriterRepository;

import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {
    @Override
    public Writer getById(Long aLong) throws Exception {
        return null;
    }

    @Override
    public boolean delete(Long aLong) throws Exception {
        return false;
    }

    @Override
    public boolean update(Long aLong) throws Exception {
        return true;
    }

    @Override
    public boolean save(Writer writer) throws Exception {
        return false;
    }

    @Override
    public Writer[] getAll() throws Exception {
        return null;
    }


}
