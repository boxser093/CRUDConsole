package com.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Label {
    @SerializedName("id")
    Long id;
    @SerializedName("name")
    String name;

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    @SerializedName("status")
    PostStatus status;

    public Label(Long id, String name, PostStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name='" + name + '\'' + ", status=" + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label guest = (Label) o;
        return id == guest.id
                && (name == guest.name
                || (name != null &&name.equals(guest.getName())))        && (status == guest.status
                || (status != null && status.equals(guest.getStatus())
        ));

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name==null) ? 0 : name.hashCode());
        result = (int) (prime * result +id);
        result = prime * result + ((status==null) ? 0: status.hashCode());
        return result;
    }
}
