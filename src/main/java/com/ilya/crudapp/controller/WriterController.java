package com.ilya.crudapp.controller;

import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.model.Writer;
import com.ilya.crudapp.repository.WriterRepository;
import com.ilya.crudapp.repository.impl.GsonWriterRepositoryImpl;
import com.ilya.crudapp.repository.jdbc.JDBCWriterRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class WriterController {
    private final WriterRepository writerRepository = new JDBCWriterRepositoryImpl();
    private final PostController postController = new PostController();
    public List<Post> preparePost(List<Long> idPosts) {
        List<Post> result = new ArrayList<>();
        idPosts.stream().forEach(x -> result.add(postController.findById(x)));
        return result;
    }
    public Writer findById(long id) {
        return writerRepository.getById(id);
    }
    public List<Writer> findAll() {
       return writerRepository.getAll();
    }
    public Writer saveWriter(String firstName,String lastName, List<Long> posts) {
        List<Post> postsWriters = preparePost(posts);
        Writer writer = Writer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .posts(postsWriters)
                .status(Status.ACTIVE)
                .build();
        return writerRepository.save(writer);
    }
    public boolean deleteById(Long idForDelete) {
        Writer writer = Writer.builder()
                .id(idForDelete)
                .status(Status.DELETED)
                .build();

        return writerRepository.deleteById(idForDelete);
    }
    public Writer updateWriter(Long id, String firstName, String lastName, List<Post> posts) {
        Writer writer = Writer.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .posts(posts)
                .build();
        return writerRepository.update(writer);
    }
}
