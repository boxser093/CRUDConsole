package com.ilya.crudapp.repository.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.model.Writer;
import com.ilya.crudapp.repository.WriterRepository;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonWriterRepositoryImpl implements WriterRepository {
    private final String failName = "writers.json.xml";
    private final Gson GSON = new Gson();
    private List<Writer> getAllWriters() {
        List<Writer> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getPath(failName)))) {
            Type type = new TypeToken<List<Writer>>() {
            }.getType();
            result = GSON.fromJson(br, type);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    private void writeAllWritersToFile(List<Writer> writers) {
        String jsonString = GSON.toJson(writers);
        try (FileWriter fw = new FileWriter(getPath(failName), false)) {
            fw.write(jsonString);
            fw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Long generateId(List<Writer> writers) {

        return writers.stream().mapToLong(Writer::getId).max().orElse(0) + 1;
    }
    @Override
    public Writer getById(Long id) {
        return getAllWriters().stream()
                .filter(l -> {
                    return l.getId().equals(id);
                }).findFirst()
                .orElse(null);
    }
    @Override
    public boolean deleteById(Long id) {
        List<Writer> writers = getAllWriters();
        writers.stream().map(writer -> {
            if (writer.getId().equals(id)) {
                writer.setStatus(Status.DELETED);
            }
            return writer;
        }).collect(Collectors.toList());
        writeAllWritersToFile(writers);
        return true;
    }
    @Override
    public Writer update(Writer writerToUpdate) {
        List<Writer> writers = getAllWriters()
                .stream()
                .map(exisitingWriter -> {
                    if (exisitingWriter.getId().equals(writerToUpdate.getId())) {
                        return writerToUpdate;
                    }

                    return exisitingWriter;
                }).collect(Collectors.toList());

        writeAllWritersToFile(writers);
        return writerToUpdate;
    }
    @Override
    public Writer save(Writer writerToUpdate) {
        List<Writer> writers = getAllWriters();
        writerToUpdate.setId(generateId(writers));
        writers.add(writerToUpdate);
        writeAllWritersToFile(writers);
        return writerToUpdate;
    }
    @Override
    public List<Writer> getAll() {
        return getAllWriters();
    }
    private static String getPath(String filename) {
        Path currentAbsolutePath = Paths.get("").toAbsolutePath();
        String path = currentAbsolutePath + "\\src\\main\\java\\resources\\" + filename;
        return path;
    }
}




