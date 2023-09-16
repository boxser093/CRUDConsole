package com.model;

import com.google.gson.annotations.SerializedName;

public class LabelData {
    public Label[] getLabels() {
        return labels;
    }

    public void setLabels(Label[] labels) {
        this.labels = labels;
    }

    @SerializedName("labels")
    Label[] labels;

    public LabelData(Label[] labels) {
        this.labels = labels;
    }
}
