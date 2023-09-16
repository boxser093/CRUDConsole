package com.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Writer {
    @SerializedName("status")
    PostStatus status;
    @SerializedName("id")
    Long id;
    @SerializedName("firstName")
    String firstName;

    public Writer(PostStatus status, Long id, String firstName, String lastName, List<Post> posts) {
        this.status = status;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    @SerializedName("lastName")
    String lastName;
    @SerializedName("posts")
    List<Post> posts;

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
