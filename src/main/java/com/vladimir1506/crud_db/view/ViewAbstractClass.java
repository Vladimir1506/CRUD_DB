package com.vladimir1506.crud_db.view;

import com.vladimir1506.crud_db.model.Region;

import java.util.List;
import java.util.Scanner;

public abstract class ViewAbstractClass {
    boolean exit = false;

    protected abstract void print();

    protected abstract void choose();

    protected abstract void create();

    protected abstract void getAll();

    protected abstract void delete();

    protected abstract void getById();

    protected abstract void update();

    public void start() {
        do {
            System.out.println();
            print();
            choose();
        } while (!exit);
    }


    public Scanner scanner() {
        return new Scanner(System.in);
    }

    public void exitMessage(int exitN) {
        System.out.println(exitN + ". Завершить программу.");
    }

}
