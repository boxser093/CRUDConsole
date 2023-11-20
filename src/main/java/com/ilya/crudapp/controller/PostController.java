package com.ilya.crudapp.controller;

import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.PostRepository;
import com.ilya.crudapp.repository.hibernate.HbPostRepositoryImpl;
import com.ilya.crudapp.repository.jdbc.JDBCPostRepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostController {
    private final PostRepository postRepository;
    private final LabelController labelController;

    public PostController(PostRepository postRepository, LabelController labelController) {
        this.postRepository = postRepository;
        this.labelController = labelController;
    }
    public PostController() {
        postRepository = new HbPostRepositoryImpl();
        labelController = new LabelController();
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
                .created(new Date())
                .build();
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
                .update(new Date())
                .content(content)
                .labels(labels)
                .status(Status.ACTIVE)
                .build();
        return postRepository.update(post);
    }
    public List<Label> prepareLabels(List<Long> idLabels) {
        List<Label> result = new ArrayList<>();
        idLabels.stream().forEach(x -> result.add(labelController.findById(x)));
        return result;
    }
}
