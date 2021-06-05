package com.vladimir1506.crud_db.repository;

import com.vladimir1506.crud_db.model.Region;

import java.util.List;

public interface RegionRepository extends GenericRepository<Region, Long> {

    List<Region> getAll();

    Region save(Region region);

    Region getById(Long id);

    Region update(Region region);

    void delete(Long id);

    Region getByName(String name);
}
