package com.ilya.crudapp.view;

import com.ilya.crudapp.controller.LabelController;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;

import java.util.Objects;
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
    public void edit() {
        System.out.println("Enter id for edit label");
        Long id = scanner.nextLong();
        System.out.println("Enter new name");
        scanner.nextLine();
        String name = scanner.nextLine();
        labelController.updateLabel(id, name, Status.ACTIVE);
        System.out.println("Update successful ");

    }
    public void delete() {
        System.out.println("Enter id for delete");
        Long tmpId = scanner.nextLong();
        labelController.deleteById(tmpId);
        System.out.println("Label is delete");
    }
    public void show() {
        System.out.println("Show all labels");
        System.out.println(labelController.findAll());
    }
    public void showContext(){
        System.out.println("1. Create label\n" +
                "2. Update label\n" +
                "3. Delete label\n" +
                "4. Show\n" +
                "5. Exit");
        int i= scanner.nextInt();

        scanner.nextLine();

            switch (i){
                case 1:
                    this.createLabel();
                    break;
                case 2:
                    this.edit();
                    break;
                case 3:
                    this.delete();
                    break;
                case 4:
                    this.show();
                    break;
                case 5:
                    break;
            }
    }

}
