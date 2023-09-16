package com.repository.impl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.Label;
import com.model.LabelData;
import com.model.PostStatus;
import com.repository.LabelRepository;

public class GsonLabelRepositoryImpl implements LabelRepository {

    private static final String pathName = "E:\\Ilya\\IDEAProject\\CRUDconsole\\src\\main\\java\\resources\\labels.json";

    @Override
    public Label getById(Long aLong) throws Exception {
        Label result = null;
        try (FileReader reader = new FileReader(pathName)) {
            LabelData results = new Gson().fromJson(reader, LabelData.class);
            if (results != null) {
                for (Label label : results.getLabels()) {
                    if (label.getId() == aLong) {
                        result = label;
                        result.setStatus(PostStatus.UNDER_REVIEW);
                    } else {
                        System.out.println("Sorry not finds, maybe try again, and tap #? for get all");
                    }
                }
            } else {
                System.out.println("Sorry data is not exist");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public boolean delete(Long id) throws Exception {
        Label result = getById(id);

        if (result == null) {
            System.out.println("Label's is not exist");
            return false;
        } else if (result.getStatus().equals(PostStatus.DELETED)) {
            System.out.println("Label's is DELETED");
            return false;
        } else {
            result.setStatus(PostStatus.DELETED);
            System.out.println("Operation is successful");
            return true;
        }
    }

    public boolean update(Long aLong) throws Exception {
        LabelData data;
        try (FileReader reader = new FileReader(pathName)) {
            data = new Gson().fromJson(reader, LabelData.class);
            if (data != null) {
                Label[] tmp = data.getLabels();
                for (Label label : tmp) {
                    if(label.getId()==aLong){
                        System.out.println("Update process is activated");
                        System.out.println("Please select line for update");
                    }
                }
            }
        }

        return true;
    }

    public boolean save(Label label) throws Exception {
        boolean result = false;
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(pathName))))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonString = gson.toJson(label);
            bw.write(gsonString);
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Write data!");
        return result;
    }

    public boolean save(LabelData label) throws Exception {
        boolean result = false;
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(pathName))))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonString = gson.toJson(label);
            bw.write(gsonString);
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Write data!");
        return result;
    }

    public Label[] getAll() throws Exception {
        LabelData data = null;
        try (FileReader fr = new FileReader(pathName)) {
            data = new Gson().fromJson(fr, LabelData.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (data != null) {
            Arrays.stream(data.getLabels()).forEach(x -> x.setStatus(PostStatus.UNDER_REVIEW));
            return data.getLabels();
        }
        System.out.println("Sorry not data! Create new data tap #");
        return null;
    }

}
