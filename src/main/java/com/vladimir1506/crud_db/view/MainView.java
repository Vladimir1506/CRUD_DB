package com.vladimir1506.crud_db.view;

public class MainView extends ViewAbstractClass {

    @Override
    protected void print() {
        System.out.println("Выберите, с какой сущностью желаете работать:");
        System.out.println("1. User.");
        System.out.println("2. Post.");
        System.out.println("3. Region.");
        exitMessage(4);
    }

    @Override
    protected void choose() {
        int choice = scanner().nextInt();
        switch (choice) {
            case 1:
                new UserView().start();
                break;
            case 2:
                new PostView().start();
            case 3:
                new RegionView().start();
            case 4:
                System.exit(0);
                break;
        }
    }

    @Override
    protected void create() {
        System.out.println("Данный функционал не реализован");
    }

    @Override
    protected void getAll() {
        System.out.println("Данный функционал не реализован");
    }

    @Override
    protected void delete() {
        System.out.println("Данный функционал не реализован");
    }

    @Override
    protected void getById() {
        System.out.println("Данный функционал не реализован");
    }

    @Override
    protected void update() {
        System.out.println("Данный функционал не реализован");
    }
}
