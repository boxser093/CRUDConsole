package com.ilya.crudapp.controller;

import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.PostRepository;
import com.ilya.crudapp.repository.impl.GsonPostRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class PostController {
    private final PostRepository postRepository = new GsonPostRepositoryImpl();
    private final LabelController labelController = new LabelController();
    public List<Label> prepareLabels(List<Long> idLabels) {
        List<Label> result = new ArrayList<>();
        idLabels.stream().forEach(x -> result.add(labelController.findById(x)));
        return result;
    }
    public Post findById(long id) {
        return postRepository.getById(id);
    }
    public List<Post> findAll() {
        return postRepository.getAll();
    }
    public Post savePost(String content, List<Long> longList) {
        Post post = Post.builder()
                .content(content)
                .labels(prepareLabels(longList))
                .status(Status.ACTIVE)
                .update(false)
                .created(true)
                .build();
        System.out.println("Create post "+post);
        return postRepository.save(post);
    }
    public boolean deleteById(Long idForDelete) {
        Post pst = Post.builder()
                .id(idForDelete)
                .status(Status.DELETED)
                .build();
        return postRepository.deleteById(idForDelete);
    }
    public Post updatePost(Long id, String content, List<Label> labels) {

        Post post = Post.builder()
                .id(id)
                .created(true)
                .update(true)
                .content(content)
                .labels(labels)
                .status(Status.ACTIVE)
                .build();
        return postRepository.update(post);
    }
}
