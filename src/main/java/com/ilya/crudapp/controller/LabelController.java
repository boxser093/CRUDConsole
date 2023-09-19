package com.ilya.crudapp.controller;

import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;
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
                .status(Status.ACTIVE)
                .build();
        return labelRepository.save(label);
    }
    public boolean deleteById(Long id) {
        Label label = Label.builder()
                .id(id)
                .status(Status.DELETED)
                .build();

        return labelRepository.deleteById(id);
    }
    public Label updateLabel(Long id, String name, Status status) {
        Label label = Label.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();

        return labelRepository.update(label);
    }


}