package com.ilya.crudapp.controller;

import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.LabelRepository;
import com.ilya.crudapp.repository.jdbc.JDBCLabelRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class LabelController {
    private final LabelRepository bdlabelRepository;

    public LabelController(LabelRepository bdlabelRepository) {
        this.bdlabelRepository = bdlabelRepository;
    }
    public LabelController(){
        bdlabelRepository = new JDBCLabelRepositoryImpl();
    }

    public Label findById(long id) {
        return bdlabelRepository.getById(id);
    }
    public List<Label> findAll() {
       return bdlabelRepository.getAll();
    }
    public Label saveLabel(String name) {
        Label label = Label.builder()
                .name(name)
                .status(Status.ACTIVE)
                .build();
        return bdlabelRepository.save(label);
    }
    public boolean deleteById(Long id) {
        Label label = Label.builder()
                .id(id)
                .status(Status.DELETED)
                .build();

        return bdlabelRepository.deleteById(id);
    }
    public Label updateLabel(Long id, String name) {
        Label label = Label.builder()
                .id(id)
                .name(name)
                .build();

        return bdlabelRepository.update(label);
    }


}
