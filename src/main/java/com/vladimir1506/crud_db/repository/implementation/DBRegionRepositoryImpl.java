package com.vladimir1506.crud_db.repository.implementation;

import com.vladimir1506.crud_db.model.Region;
import com.vladimir1506.crud_db.repository.RegionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBRegionRepositoryImpl implements RegionRepository {
    private final String GETALL = "select * from regions r " +
            "left join users u on r.id=u.region_id " +
            "order by r.id asc";
    private final String SAVE = "insert into regions (name) values ('%s')";
    private final String UPDATE = "update regions set name='%s' where id=%d";
    private final String DELETE = "delete from regions where id=%d";
    private final String ID = "select last_insert_id()";

    @Override
    public List<Region> getAll() {
        List<Region> regions = new ArrayList<>();
        try {
            ResultSet resultSet = Connect.getStatement(GETALL).executeQuery();
            while (resultSet.next()) {
                Long id = (long) resultSet.getInt("r.id");
                String name = resultSet.getString("r.name");
                regions.add(new Region(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return regions;
    }

    @Override
    public Region save(Region region) {
        try {
            String saveQuery = String.format(SAVE, region.getName());
            Connect.getStatement(saveQuery).execute();
            region.setId(getLastId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return region;
    }

    @Override
    public Region getById(Long id) {
        List<Region> regions;
        Region wantedRegion = null;
        regions = getAll();
        if (regions != null) {
            for (Region region : regions
            ) {
                if (region.getId().equals(id)) {
                    wantedRegion = region;
                }
            }
        }
        return wantedRegion;
    }

    @Override
    public Region update(Region region) {
        String updateQuery;
        updateQuery = String.format(UPDATE, region.getName(), region.getId());
        try {
            Connect.getStatement(updateQuery).execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return region;
    }

    @Override
    public void delete(Long id) {
        String deleteQuery;
        deleteQuery = String.format(DELETE, id);
        try {
            Connect.getStatement(deleteQuery).execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Region getByName(String name) {
        Region wantedRegion = null;
        List<Region> regions = getAll();
        if (regions != null) {
            for (Region region : regions
            ) {
                if (region.getName().equals(name)) {
                    wantedRegion = region;
                }
            }
        }
        return wantedRegion;
    }

    public Long getLastId() {
        Long lastId = null;
        try {
            ResultSet resultSet = Connect.getStatement(ID).executeQuery();
            while (resultSet.next()) {
                lastId = resultSet.getLong("last_insert_id()");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lastId;
    }
}
