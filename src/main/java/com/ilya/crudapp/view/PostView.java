package com.ilya.crudapp.view;

import com.ilya.crudapp.controller.PostController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);
    private final PostController postController = new PostController();

    public void createPost() {
        System.out.println("Please inter content for post");
        String content = scanner.nextLine();
        System.out.println("Please inter number of labels to create post \n" +
                "and enter 'stop' for end");
        List<Long> longList = inputLong();
        postController.savePost(content, longList);
    }

    public void edit() {
        System.out.println(
                "Update the post \n" +
                        "Please choose update Mode\n" +
                        "1 Update only content \n" +
                        "2 Update only labels \n" +
                        "3 Update content whit labels \n" +
                        "4 Exit");
        int mode = scanner.nextInt();
        switch (mode) {
            case 1:
                System.out.println("Please choose post and input id");
                Long id = scanner.nextLong();
                scanner.nextLine();

                System.out.println("Input new content");
                String newContent = scanner.nextLine();

                postController.updatePost(id, newContent, postController.findById(id).getLabels());
                System.out.println("Successful update");
                break;
            case 2:
                System.out.println("Please choose post and input id");
                Long id2 = scanner.nextLong();
                scanner.nextLine();

                System.out.println("Show all Label in you post");
                postController.findById(id2).getLabels().stream().forEach(System.out::println);

                System.out.println("Choose new labels");
                System.out.println("Please inter id's of labels \n" +
                                            "and enter 'stop' for end");
                List<Long> numbers = inputLong();
                postController.updatePost(id2, postController.findById(id2).getContent(), postController.prepareLabels(numbers));
                System.out.println("Successful update");
                break;
            case 3:
                System.out.println("Please choose post and input id");
                Long id3 = scanner.nextLong();
                scanner.nextLine();

                System.out.println("Please input new content");
                String newContent3 = scanner.nextLine();

                System.out.println("Please inter id's of labels \n" +
                        "and enter 'stop' for end");
                List<Long> numbers3 = inputLong();

                postController.updatePost(id3, newContent3, postController.prepareLabels(numbers3));
                System.out.println("Successful update");
                break;
            case 4:
                break;
        }
    }
    public void delete() {

        System.out.println("Please inter id of post for delete");
        Long id = scanner.nextLong();

        postController.deleteById(id);
    }
    public void show() {
        System.out.println("All posts");
        postController.findAll().stream().forEach(System.out::println);
    }
    public List<Long> inputLong(){
        List<Long> result = new ArrayList<>();
        while (true) {
            if (scanner.hasNextLong()) {
                result.add(scanner.nextLong());
            } else if (scanner.hasNextLine()) {
                String stop = scanner.nextLine();
                if(stop.equalsIgnoreCase("stop")){
                    break;
                }
            } else {
                System.out.println("try again");
            }
        }
        return result;
    }
    public void showContext(){
        System.out.println("1. Create post\n" +
                "2. Update post\n" +
                "3. Delete post\n" +
                "4. Show\n" +
                "5. Exit");

        int i= scanner.nextInt();
        scanner.nextLine();

            switch (i){
                case 1:
                    createPost();
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
