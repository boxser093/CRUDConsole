package com.ilya.crudapp.view;


import java.util.Scanner;

public class ViewExecutor {
    Scanner scanner = new Scanner(System.in);
    LabelView labelView = new LabelView();
    PostView postView = new PostView();
    WriteView writeView = new WriteView();

    public void run() {
        int mode=0;
        while (mode!=4) {
            System.out.println("Chose section \n" +
                    "1. Labels\n" +
                    "2. Posts\n" +
                    "3. Writers\n" +
                    "4. Exit");
            mode = scanner.nextInt();
            switch (mode) {
                case 1:
                    labelView.showContext();
                    System.out.println("______________________________________________");
                    break;
                case 2:
                    postView.showContext();
                    System.out.println("______________________________________________");
                    break;
                case 3:
                    writeView.showContext();
                    System.out.println("______________________________________________");
                    break;
                case 4:
                    System.out.println("______________________________________________");
                    System.out.println("Exit of crud app");
                    break;
                default:
                    System.out.println("Incorrect input and try again");
                    break;
            }
        }
    }
}
