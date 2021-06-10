package com.vladimir1506.crud_db.view;

import com.vladimir1506.crud_db.controller.PostController;
import com.vladimir1506.crud_db.model.Post;

import java.util.List;

public class PostView extends ViewAbstractClass {

    private final PostController postController = new PostController();
    List<Post> posts = postController.getAll();


    protected void print() {
        System.out.println("Введите номер действия, которые желаете произвести:");
        System.out.println("1. Вывести список всех постов.");
        System.out.println("2. Вывести пост по заданному id.");
        System.out.println("3. Добавить новый пост.");
        System.out.println("4. Удалить пост.");
        System.out.println("5. Изменить содержимое поста.");
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
        posts = postController.getAll();
        if (!posts.isEmpty()) {
            System.out.println("Список постов:");
            System.out.println(posts);
        } else System.out.println("Ни одного поста не создано");

    }

    public void create() {
        System.out.println("Введите содержимое поста:");
        String postContent = scanner().nextLine();
        Post post = postController.createPost(postContent);
        System.out.println("Вы добавили новый пост: " + post);
    }

    public void delete() {
        if (isPostsEmpty()) return;
        this.getAll();
        System.out.println("Введите id поста, который желаете удалить:");
        Long id = scanner().nextLong();
        Post deletedPost = postController.getPostById(id);
        postController.deletePost(id);
        System.out.println("Удалён пост " + deletedPost);
        this.getAll();
    }

    public void getById() {
        if (isPostsEmpty()) return;
        System.out.println("Введите id поста, который желаете получить:");
        Long id = scanner().nextLong();
        Post post = postController.getPostById(id);
        System.out.println("Запрашиваемый пост: " + post);
    }

    public void update() {
        if (isPostsEmpty()) return;
        System.out.println("Введите id поста, содержимое которого хотите изменить:");
        Long id = scanner().nextLong();
        System.out.println("Введите новое содержимое поста:");
        String content = scanner().nextLine();
        postController.updatePost(id, content);
        this.getAll();
    }

    private boolean isPostsEmpty() {
        if (posts.isEmpty()) {
            getAll();
            return true;
        } else return false;
    }
}
