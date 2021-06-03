package com.vladimir1506.crud_db.repository.implementation;

import com.vladimir1506.crud_db.model.Region;
import com.vladimir1506.crud_db.repository.RegionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBRegionRepositoryImpl implements RegionRepository {
    private final String URL = "jdbc:mysql://localhost:3306/crud_db";
    private final String USERNAME = "rootroot";
    private final String PASSWORD = "rootroot";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    Connection connection;
    Statement statement;
    ResultSet resultSet;


    @Override
    public Region save(Region region) {
        try {
            List<Region> regions = getAll();
            region = new Region(generateID(regions), region.getName());
            String saveQuery = String.format("insert into regions (id,name) values (%d,'%s')", region.getId(), region.getName());
            getStatement().execute(saveQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return region;
    }

    @Override
    public List<Region> getAll() {
        List<Region> regions = new ArrayList<>();
        try {
            resultSet = getStatement().executeQuery("select * from regions order by id asc");
            while (resultSet.next()) {
                Long id = (long) resultSet.getInt("id");
                String name = resultSet.getString("name");
                regions.add(new Region(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return regions;
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
        updateQuery = String.format("update regions set name='%s' where id=%d", region.getName(), region.getId());
        try {
            getStatement().execute(updateQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return region;
    }

    @Override
    public void delete(Long id) {
        String deleteQuery;
        deleteQuery = String.format("delete from regions where id=%d", id);
        try {
            getStatement().execute(deleteQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
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

    private Statement getStatement() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    private Long generateID(List<Region> list) {
        return list.stream().map(Region::getId).max(Long::compare).orElse(null) + 1;
    }
}
