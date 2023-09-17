package com.ilya.crudapp.repository.impl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.PostStatus;
import com.ilya.crudapp.repository.LabelRepository;

public class GsonLabelRepositoryImpl implements LabelRepository {

    //TODO: make relative path
    private final String pathName = "E:\\Ilya\\IDEAProject\\CRUDconsole\\src\\main\\java\\resources\\labels.json";
    private final Gson GSON = new Gson();

    private List<Label> getAllLabels() {
        //TODO: read string and convert to List<Labels>
        return new ArrayList<>();
    }

    private void writeAllLabelsToFile(List<Label> labels) {
        String jsonString = GSON.toJson(labels);
        //TODO: write string to FILE
    }

    private Long generateId(List<Label> labels) {
        return labels.stream().mapToLong(Label::getId).max().orElse(0) + 1;
    }


    @Override
    public Label getById(Long id) {
        return getAllLabels().stream()
                .filter(l -> {
                    return l.getId().equals(id);
                }).findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        List<Label> labels = getAllLabels();
        labels.removeIf(label -> label.getId().equals(id));
        writeAllLabelsToFile(labels);
        return true;
    }

    @Override
    public Label update(Label labelToUpdate) {
        List<Label> labels = getAllLabels()
                .stream()
                .map(exisitingLabel -> {
                    if(exisitingLabel.getId().equals(labelToUpdate.getId())) {
                        return labelToUpdate;
                    }

                    return exisitingLabel;
                }).collect(Collectors.toList());

        writeAllLabelsToFile(labels);
        return labelToUpdate;
    }

    @Override
    public Label save(Label labelToCreate) {
        List<Label> labels = getAllLabels();
        labelToCreate.setId(generateId(labels));
        labels.add(labelToCreate);
        writeAllLabelsToFile(labels);
        return labelToCreate;
    }

    @Override
    public List<Label> getAll() {
        return getAllLabels();
    }
}
