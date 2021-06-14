package com.vladimir1506.crud_db.view;

import com.vladimir1506.crud_db.controller.RegionController;
import com.vladimir1506.crud_db.model.Region;

import java.util.List;

public class RegionView extends ViewAbstractClass {
    private final RegionController regionController = new RegionController();


    public void print() {
        System.out.println("Введите номер действия, которые желаете произвести:");
        System.out.println("1. Вывести список всех регионов.");
        System.out.println("2. Вывести регион по заданному id.");
        System.out.println("3. Добавить новый регион.");
        System.out.println("4. Удалить регион.");
        System.out.println("5. Изменить наименование региона.");
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
        System.out.println("Введите название региона:");
        String regionName = scanner().nextLine();
        Region region = regionController.createRegion(regionName);
        System.out.println("Вы добавили новый регион: " + region);
    }

    public void getAll() {
        List<Region> regions = regionController.getAll();
        if (!regions.isEmpty()) {
            System.out.println("Список регионов:");
            System.out.println(regions);
        } else System.out.println("Список регионов пуст");
    }

    public void delete() {
        if (isRegionsEmpty()) return;
        System.out.println("Введите id региона, который желаете удалить:");
        Long id = scanner().nextLong();
        Region deletedRegion = regionController.getRegionById(id);
        regionController.deleteRegion(id);
        System.out.println("Удалён регион " + deletedRegion);
        this.getAll();
    }

    public void getById() {
        if (isRegionsEmpty()) return;
        System.out.println("Введите id региона, который желаете получить:");
        Long id = scanner().nextLong();
        Region region = regionController.getRegionById(id);
        System.out.println("Запрашиваемый регион: " + region);
    }

    public void update() {
        if (isRegionsEmpty()) return;
        System.out.println("Введите id региона, который хотите изменить:");
        Long id = scanner().nextLong();
        System.out.println("Введите новое название региона:");
        String name = scanner().nextLine();
        regionController.updateRegion(id, name);
        this.getAll();
    }

    private boolean isRegionsEmpty() {
        List<Region> regions = regionController.getAll();
        if (regions.isEmpty()) {
            getAll();
            return true;
        } else return false;
    }
}
