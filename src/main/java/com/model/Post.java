package com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Post {
    @SerializedName("id")
    Long id;
    @SerializedName("content")
    String content;
    @SerializedName("created")
    boolean created;
    @SerializedName("update")
    boolean update;
    @SerializedName("status")
    PostStatus status;
    @SerializedName("labels")
    List<Label> labels;

    public Post(Long id, String content, boolean created, boolean update, PostStatus status, List<Label> labels) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.update = update;
        this.status = status;
        this.labels = labels;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
