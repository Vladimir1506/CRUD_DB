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

    public void create() {
        System.out.println("Введите имя пользователя:");
        String userFName = scanner().nextLine();
        System.out.println("Введите фамилию пользователя:");
        String userLName = scanner().nextLine();
        System.out.println("Введите список id постов пользователя через запятую (Например `1,2`:");
        String post = scanner().nextLine();
        List<Post> listOfPosts = new ArrayList<>();
        for (String el : post.split(",")) {
            Post postById = new PostController().getPostById(Long.parseLong(el));
            listOfPosts.add(postById);
        }
        System.out.println("Выберите регион пользователя: ");
        System.out.println(new RegionController().getAll());
        Region region = new RegionController().getRegionById(Long.parseLong(scanner().nextLine()));
        System.out.println("Введите роль пользователя из перечисленных вариантов");
        System.out.println(Arrays.toString(Role.values()));
        Role role = Role.valueOf(scanner().nextLine());
        User newUser = userController.createUser(userFName, userLName, listOfPosts, region, role);
        System.out.println("Вы добавили нового пользователя:");
        System.out.println(newUser);
    }

    public void getAll() {
        System.out.println("Список пользователей:");
        userController.getAll().forEach(System.out::println);
    }

    public void delete() {
        this.getAll();
        System.out.println("Введите id пользователя, которого желаете удалить:");
        Long id = scanner().nextLong();
        User deletedUser = userController.getUserById(id);
        userController.deleteUser(id);
        System.out.println("Удалён пользователь " + deletedUser);
        this.getAll();
    }

    public void getById() {
        System.out.println("Введите id пользователя, который желаете получить:");
        Long id = scanner().nextLong();
        User user = userController.getUserById(id);
        System.out.println("Запрашиваемый пользователь: ");
        System.out.println(user);
    }

    public void update() {
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
        Role role = Role.valueOf(scanner().nextLine());
        userController.updateUser(id, firstName, lastName, listOfPosts, region, role);
        this.getAll();
    }
}
