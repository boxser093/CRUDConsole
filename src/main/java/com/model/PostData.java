package com.model;

import com.google.gson.annotations.SerializedName;

public class PostData {
    @SerializedName("posts")
    Post[] posts;
}
