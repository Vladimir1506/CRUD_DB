package com.vladimir1506.crud_db.controller;

import com.vladimir1506.crud_db.model.Region;
import com.vladimir1506.crud_db.repository.RegionRepository;
import com.vladimir1506.crud_db.repository.implementation.DBRegionRepositoryImpl;

import java.util.List;

public class RegionController {

    private final RegionRepository regionRepository;

    public RegionController() {
        this.regionRepository = new DBRegionRepositoryImpl();
    }

    public Region createRegion(String name) {
        Region region = new Region(null, name);
        return regionRepository.save(region);
    }

    public List<Region> getAll() {
        return regionRepository.getAll();
    }

    public void deleteRegion(Long id) {
        regionRepository.delete(id);
    }

    public Region getRegionById(Long id) {
        return regionRepository.getById(id);
    }

    public void updateRegion(Long id, String name) {
        Region updatedRegion = new Region(id, name);
        regionRepository.update(updatedRegion);
    }
}
