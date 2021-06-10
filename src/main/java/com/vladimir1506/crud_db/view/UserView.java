package com.vladimir1506.crud_db.view;

import com.vladimir1506.crud_db.controller.PostController;
import com.vladimir1506.crud_db.controller.RegionController;
import com.vladimir1506.crud_db.controller.UserController;
import com.vladimir1506.crud_db.model.Post;
import com.vladimir1506.crud_db.model.Region;
import com.vladimir1506.crud_db.model.Role;
import com.vladimir1506.crud_db.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserView extends ViewAbstractClass {
    private final UserController userController = new UserController();
    List<User> users= userController.getAll();
    RegionController regionController = new RegionController();
    PostController postController = new PostController();
    List<Region> regions = regionController.getAll();
    Region region;
    List<Post> posts = postController.getAll();


    protected void print() {
        System.out.println("Введите номер действия, которые желаете произвести:");
        System.out.println("1. Вывести список всех пользователей.");
        System.out.println("2. Вывести информацию о пользователе по заданному id.");
        System.out.println("3. Добавить нового пользователя.");
        System.out.println("4. Удалить пользователя.");
        System.out.println("5. Изменить информацию о пользователе.");
        System.out.println("6. Вернуться к выбору сущности.");
        exitMessage(7);
    }

    protected void choose() {
        int choice = scanner().nextInt();
        switch (choice) {
            case 1:
                this.getAll();
                break;
            case 2:
                this.getById();
                break;
            case 3:
                this.create();
                break;
            case 4:
                this.delete();
                break;
            case 5:
                this.update();
                break;
            case 6:
                new MainView().start();
            case 7:
                System.exit(0);
                break;
        }
    }

    public void getAll() {
        users = userController.getAll();
        if (!users.isEmpty()) {
            System.out.println("Список пользователей:");
            System.out.println(users);
        } else System.out.println("Ни одного пользователя не создано");
    }

    public void create() {
        List<Post> userPosts = new ArrayList<>();
        System.out.println("Введите имя пользователя:");
        String userFName = scanner().nextLine();
        System.out.println("Введите фамилию пользователя:");
        String userLName = scanner().nextLine();
        if (!posts.isEmpty()) {
            System.out.println("Введите список id постов пользователя через запятую (Например `1,2`:");
            String post = scanner().nextLine();
            for (String el : post.split(",")) {
                Post postById = postController.getPostById(Long.parseLong(el));
                if (postById != null) {
                    userPosts.add(postById);
                }
            }
        }

        if (regions.isEmpty()) {
            System.out.println("Список регионов пуст, введите название нового региона для этого пользователя:");
            region = regionController.createRegion(scanner().nextLine());
        } else {
            System.out.println("Выберите регион пользователя:");
            System.out.println(regions);
            region = regionController.getRegionById(Long.parseLong(scanner().nextLine()));
        }
        System.out.println("Введите роль пользователя из перечисленных вариантов");
        System.out.println(Arrays.toString(Role.values()));
        Role role = Role.valueOf(scanner().nextLine().toUpperCase());
        User newUser = userController.createUser(userFName, userLName, userPosts, region, role);
        System.out.println("Вы добавили нового пользователя:");
        System.out.println(newUser);
    }

    public void delete() {
        if (isUsersEmpty()) return;
        System.out.println("Введите id пользователя, которого желаете удалить:");
        Long id = scanner().nextLong();
        User deletedUser = userController.getUserById(id);
        userController.deleteUser(id);
        System.out.println("Удалён пользователь " + deletedUser);
        this.getAll();
    }

    public void getById() {
        if (isUsersEmpty()) return;
        System.out.println("Введите id пользователя, который желаете получить:");
        Long id = scanner().nextLong();
        User user = userController.getUserById(id);
        System.out.println("Запрашиваемый пользователь: ");
        System.out.println(user);
    }

    public void update() {
        if (isUsersEmpty()) return;
        System.out.println("Введите id пользователя, которого хотите изменить:");
        Long id = scanner().nextLong();
        System.out.println("Введите новое имя пользователя:");
        String firstName = scanner().nextLine();

        System.out.println("Введите новую фамилию пользователя:");
        String lastName = scanner().nextLine();
        System.out.println("Введите новый список id постов пользователя через запятую (Например `1,2`:");
        String post = scanner().nextLine();
        List<Post> listOfPosts = Arrays.stream(post.split(","))
                .map(el -> new PostController().getPostById(Long.parseLong(el))).collect(Collectors.toList());
        System.out.println("Выберите новый регион пользователя: ");
        System.out.println(new RegionController().getAll());
        Region region = new RegionController().getRegionById(Long.parseLong(scanner().nextLine()));
        System.out.println("Введите новую роль пользователя из перечисленных вариантов");
        System.out.println(Arrays.toString(Role.values()));
        Role role = Role.valueOf(scanner().nextLine().toUpperCase());
        userController.updateUser(id, firstName, lastName, listOfPosts, region, role);
        this.getAll();
    }

    private boolean isUsersEmpty() {
        if (users.isEmpty()) {
            getAll();
            return true;
        } else return false;
    }
}
