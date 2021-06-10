package com.vladimir1506.crud_db.repository.implementation;

import com.vladimir1506.crud_db.model.Region;
import com.vladimir1506.crud_db.repository.RegionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBRegionRepositoryImpl implements RegionRepository {

    @Override
    public List<Region> getAll() {
        List<Region> regions = new ArrayList<>();
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        try {
            ResultSet resultSet = statement.executeQuery("select * from regions r " +
                    "left join users u on r.id=u.region_id " +
                    "order by r.id asc");
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
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        try {
            List<Region> regions = getAll();
            region = new Region(generateID(regions), region.getName());

            String saveQuery = String.format("insert into regions (id,name) values (%d,'%s')", region.getId(), region.getName());
            statement.execute(saveQuery);
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
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        updateQuery = String.format("update regions set name='%s' where id=%d", region.getName(), region.getId());
        try {
            statement.execute(updateQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return region;
    }

    @Override
    public void delete(Long id) {
        String deleteQuery;
        Connect connect = new Connect();
        Statement statement = connect.getStatement();
        deleteQuery = String.format("delete from regions where id=%d", id);
        try {
            statement.execute(deleteQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Region getByName(String name) {
        List<Region> regions;
        Region wantedRegion = null;
        regions = getAll();
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

    private Long generateID(List<Region> list) {
        return list.stream().map(Region::getId).max(Long::compare).orElse(0L) + 1;
    }
}
