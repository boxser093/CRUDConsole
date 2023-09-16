package com;

import com.google.gson.JsonArray;
import com.model.Label;
import com.controller.*;
import com.model.LabelData;
import com.model.PostStatus;
import com.repository.impl.GsonLabelRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LabelController controller = new LabelController(new GsonLabelRepositoryImpl());

        Label label1 = new Label(1L, "Petr", PostStatus.ACTIVE);
        Label label2 = new Label(2L, "Fedor", PostStatus.ACTIVE);
        Label label3 = new Label(3L, "Michail", PostStatus.ACTIVE);
        Label label4 = new Label(4L, "Albert", PostStatus.ACTIVE);
        controller.saveLabel(new LabelData(new Label[]{label1,label2,label3,label4}));


        System.out.println(controller.findById(1));
        System.out.println(controller.findById(2));
        Arrays.stream(controller.findAll()).forEach(System.out::println);

//        controller.saveLabel(label1);
//        controller.saveLabel(label2);
//        List<Label> result = controller.findAll();
//        result.stream().forEach(System.out::println);
    }
}
