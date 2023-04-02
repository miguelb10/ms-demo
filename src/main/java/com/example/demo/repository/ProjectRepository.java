package com.example.demo.repository;

import com.example.demo.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByPermalink(String permalink);

    @Query("SELECT DISTINCT p FROM Project p " +
            "LEFT JOIN p.areas a LEFT JOIN p.capacities c " +
            "WHERE (COALESCE(:areas, null) IS NULL OR a.name IN :areas) " +
            "AND (COALESCE(:capacities, null) IS NULL OR c.unit IN :capacities) " +
            "ORDER BY p.id ASC")
    List<Project> findByAreasAndCapacities(@Param("areas") List<String> areas, @Param("capacities") List<String> capacities);

    @Query("SELECT DISTINCT p FROM Project p " +
            "LEFT JOIN p.areas a LEFT JOIN p.capacities c " +
            "WHERE (COALESCE(:areas, null) IS NULL OR a.name IN :areas) " +
            "AND (COALESCE(:capacities, null) IS NULL OR c.unit IN :capacities) " +
            "ORDER BY p.id ASC")
    Page<Project> findByAreasAndCapacitiesPageable(@Param("areas") List<String> areas, @Param("capacities") List<String> capacities, Pageable pageable);

    List<Project> findDistinctByAreasNameInAndCapacitiesUnitIn(List<String> areas, List<String> capacities);
}
