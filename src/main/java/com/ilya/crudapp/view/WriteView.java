package com.ilya.crudapp.view;

import com.ilya.crudapp.controller.WriterController;
import com.ilya.crudapp.model.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriteView {
    private final Scanner scanner = new Scanner(System.in);
    private final WriterController writerController = new WriterController();

    public void createWriter() {
        System.out.println("For create new writer please input data \n " +
                "Input first Name");
        scanner.nextLine();
        String firstName = scanner.nextLine();
        System.out.println("and last Name");
        String lastName = scanner.nextLine();

        System.out.println("So and choose posts \n" +
                "and input 'stop' for end");
        List<Long> ideasPost = inputLong();

        System.out.println( writerController.saveWriter(firstName, lastName, ideasPost));
    }

    public void edit() {
        System.out.println("For edit data writers \n" +
                "please input id");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Please choose mode to edit date \n" +
                "1 Update first and last name \n" +
                "2 Update posts \n" +
                "3 Update first and last name and posts \n " +
                "4 Exit");
        int mode = scanner.nextInt();
        switch (mode) {
            case 1:
                System.out.println("first name");
                scanner.nextLine();
                String firstName = scanner.nextLine();
                System.out.println("last name");
                String lastName = scanner.nextLine();
                writerController.updateWriter(id, firstName, lastName, writerController.findById(id).getPosts());
                System.out.println("Update successful");
                break;
            case 2:
                System.out.println("Please input id of post and stop to exit");
                List<Long> ideaNew = inputLong();
                writerController.updateWriter(id, writerController.findById(id).getFirstName(), writerController.findById(id).getLastName(), writerController.preparePost(ideaNew));
                System.out.println("Update successful");
                break;
            case 3:
                System.out.println("first name");
                String firstName1 = scanner.nextLine();
                System.out.println("last name");
                String lastName2 = scanner.nextLine();
                System.out.println("Please input id of post and stop to exit");
                List<Long> ideaNew2 = inputLong();
                writerController.updateWriter(id, firstName1, lastName2, writerController.preparePost(ideaNew2));
                System.out.println("Update successful");
                break;
            case 4:
                System.out.println("Return");
                break;
        }
    }

    public void delete() {
        System.out.println("Enter id for delete writer");
        Long tmpId = scanner.nextLong();
        writerController.deleteById(tmpId);
        System.out.println("Writer is delete");

    }

    public void show() {
        System.out.println("Show all Writers");
        writerController.findAll().stream().forEach(System.out::println);
    }

    public List<Long> inputLong() {
        List<Long> result = new ArrayList<>();
        while (true) {
            if (scanner.hasNextLong()) {
                result.add(scanner.nextLong());
            } else if (scanner.hasNextLine()) {
                String stop = scanner.nextLine();
                if (stop.equalsIgnoreCase("stop")) {
                    break;
                }
            } else {
                System.out.println("try again");
            }
        }
        return result;
    }

    public void showContext() {
        System.out.println("1. Create writer\n" +
                "2. Update writer\n" +
                "3. Delete writer\n" +
                "4. Show\n" +
                "5. Exit");
        int i = scanner.nextInt();
            switch (i) {
                case 1:
                    createWriter();
                    break;
                case 2:
                    edit();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    show();
                    break;
                default:
                    System.out.println("Incorrect input and try again");
                    break;
            }
    }
}
