package com.repository;

import com.model.Label;
import com.model.Post;
import com.model.Writer;

public interface PostRepository extends GenericRepository<Post, Long> {

    boolean isContainLabel(Label label) throws Exception;

    boolean isContainWriter(Writer writer) throws Exception;

    void checkEdit(Long id) throws Exception;

    void checkLabel(Long id) throws Exception;

    void checkWriter(Long id) throws Exception;
}
