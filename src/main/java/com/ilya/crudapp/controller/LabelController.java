package com.ilya.crudapp.controller;

import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.PostStatus;
import com.ilya.crudapp.repository.LabelRepository;
import com.ilya.crudapp.repository.impl.GsonLabelRepositoryImpl;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();


    public Label findById(long id) {
        return labelRepository.getById(id);
    }

    public List<Label> findAll() {
       return labelRepository.getAll();
    }

    public Label saveLabel(String name) {
        Label label = Label.builder()
                .name(name)
                .status(PostStatus.ACTIVE)
                .build();
        return labelRepository.save(label);
    }

    public boolean deleteById(Long id) {
        return labelRepository.deleteById(id);
    }

    public Label updateLabel(Long id, String name, PostStatus status) {
        Label label = Label.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();

        return labelRepository.update(label);
    }
}
