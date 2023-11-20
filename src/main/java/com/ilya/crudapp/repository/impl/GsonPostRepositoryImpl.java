package com.ilya.crudapp.repository.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.PostRepository;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonPostRepositoryImpl implements PostRepository {
    private final String failName = "posts.json.xml";
    private final Gson GSON = new Gson();
    private List<Post> getAllPost() {
        List<Post> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getPath(failName)))) {
            Type type = new TypeToken<List<Post>>() {
            }.getType();
            result = GSON.fromJson(br, type);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    private void writeAllPostToFile(List<Post> posts) {
        String jsonString = GSON.toJson(posts);
        try (FileWriter fw = new FileWriter(getPath(failName), false)) {
            fw.write(jsonString);
            fw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Long generateId(List<Post> posts) {

        return posts.stream().mapToLong(Post::getId).max().orElse(0) + 1;
    }
    @Override
    public Post getById(Long id) {
        return getAllPost().stream()
                .filter(l -> {
                    return l.getId().equals(id);
                }).findFirst()
                .orElse(null);
    }
    @Override
    public boolean deleteById(Long id) {
        List<Post> posts = getAllPost();
        posts.stream().map(post -> {
            if (post.getId().equals(id)) {
                post.setStatus(Status.DELETED);
            }
            return post;
        }).collect(Collectors.toList());
        writeAllPostToFile(posts);
        return true;
    }
    @Override
    public Post update(Post postToUpdate) {
        List<Post> posts = getAllPost()
                .stream()
                .map(exisitingPost -> {
                    if (exisitingPost.getId().equals(postToUpdate.getId())) {
                        return postToUpdate;
                    }

                    return exisitingPost;
                }).collect(Collectors.toList());

        writeAllPostToFile(posts);
        return postToUpdate;
    }
    @Override
    public Post save(Post postSave) {
        List<Post> posts = getAllPost();
        postSave.setId(generateId(posts));
        posts.add(postSave);
        writeAllPostToFile(posts);
        return postSave;
    }
    @Override
    public List<Post> getAll() {
        return getAllPost();
    }
    private static String getPath(String filename) {
        Path currentAbsolutePath = Paths.get("").toAbsolutePath();
        String path = currentAbsolutePath + "\\src\\main\\java\\resources\\" + filename;
        return path;
    }
}




