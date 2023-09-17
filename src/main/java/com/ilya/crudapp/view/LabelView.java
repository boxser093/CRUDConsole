package com.ilya.crudapp.view;

import com.ilya.crudapp.controller.LabelController;
import com.ilya.crudapp.model.Label;

import java.util.Scanner;

public class LabelView {
    private final Scanner scanner = new Scanner(System.in);

    private final LabelController labelController = new LabelController();

    public void createLabel() {
        System.out.println("Enter name");
        String name = scanner.nextLine();

        Label cratedLabel = labelController.saveLabel(name);

        System.out.println("created label:" + cratedLabel);
    }

}
