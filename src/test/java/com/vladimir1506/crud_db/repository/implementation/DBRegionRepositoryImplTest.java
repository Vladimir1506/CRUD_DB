package com.vladimir1506.crud_db.repository.implementation;

import com.vladimir1506.crud_db.model.Region;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DBRegionRepositoryImplTest {
    DBRegionRepositoryImpl dbRegionRepository;
    Region reg;

    @Before
    public void setUp() {
        List<Region> regions = new ArrayList<>();
        reg = new Region(1L, "US");
        regions.add(reg);
        dbRegionRepository = mock(DBRegionRepositoryImpl.class);
        when(dbRegionRepository.getAll()).thenReturn(regions);

        when(dbRegionRepository.getById(1L)).thenReturn(reg);

        doReturn(new Region(1L, "EU"))
                .when(dbRegionRepository).update(reg);

        when(dbRegionRepository.getByName("US")).thenReturn(reg);
    }

    @Test
    public void getAllTest() {
        List<Region> regionsForTest = new ArrayList<>();
        regionsForTest.add(new Region(1L, "US"));
        List<Region> regions = dbRegionRepository.getAll();
        assertEquals(regionsForTest.size(), regions.size());
        assertEquals(regionsForTest.get(0).getId(),
                regions.get(0).getId());
        assertEquals(regionsForTest.get(0).getName(),
                regions.get(0).getName());
        verify(dbRegionRepository).getAll();
    }

    @Test
    public void getByIdTest() {
        Region region = new Region(1L, "US");
        Region regionById = dbRegionRepository.getById(1L);

        assertEquals(region.getId(), regionById.getId());
        assertEquals(region.getName(), regionById.getName());

        verify(dbRegionRepository, atLeastOnce()).getById(1L);
    }

    @Test
    public void getByNameTest() {
        Region region = new Region(1L, "US");
        Region regionByName = dbRegionRepository.getByName("US");

        assertEquals(region.getId(), regionByName.getId());
        assertEquals(region.getName(), regionByName.getName());

        verify(dbRegionRepository, atLeastOnce()).getByName("US");
    }

    @Test
    public void updateTest() {
        Region region = new Region(1L, "US");
        region.setName("EU");
        Region updatedRegion = dbRegionRepository.
                update(reg);

        assertEquals(region.getId(), updatedRegion.getId());
        assertEquals(region.getName(), updatedRegion.getName());

        verify(dbRegionRepository).update(reg);
    }



}