package com.ilya.crudapp.repository.impl;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.LabelRepository;

public class GsonLabelRepositoryImpl implements LabelRepository {

    //TODO: make relative path
    private final String failName = "labels.json";
    private final Gson GSON = new Gson();

    private List<Label> getAllLabels() {

        //TODO: read string and convert to List<Labels>
        List<Label> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getPath(failName)))) {
            Type type = new TypeToken<List<Label>>() {
            }.getType();
            result = GSON.fromJson(br, type);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void writeAllLabelsToFile(List<Label> labels) {
        //TODO: write string to FILE
        String jsonString = GSON.toJson(labels);
        try (FileWriter fw = new FileWriter(getPath(failName), false)) {
            fw.write(jsonString);
            fw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        labels.stream().map(label -> {
            if (label.getId().equals(id)) {
                label.setStatus(Status.DELETED);
            }
            return label;
        }).collect(Collectors.toList());
        writeAllLabelsToFile(labels);
        return true;
    }

    @Override
    public Label update(Label labelToUpdate) {
        List<Label> labels = getAllLabels()
                .stream()
                .map(exisitingLabel -> {
                    if (exisitingLabel.getId().equals(labelToUpdate.getId())) {
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

    private static String getPath(String filename) {
        Path currentAbsolutePath = Paths.get("").toAbsolutePath();
        String path = currentAbsolutePath + "\\src\\main\\java\\resources\\" + filename;
        return path;
    }
}




