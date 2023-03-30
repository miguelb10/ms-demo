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

    @Query("SELECT DISTINCT p FROM Project p JOIN p.areas a " +
            "WHERE :areas IS NULL OR a.name IN :areas ORDER BY p.id ASC")
    List<Project> findByAreas(@Param("areas") List<String> areaNames);

    @Query("SELECT DISTINCT p FROM Project p JOIN p.areas a " +
            "WHERE :areas IS NULL OR a.name IN :areas ORDER BY p.id ASC")
    Page<Project> findByAreasPageable(@Param("areas") List<String> areaNames, Pageable pageable);
}
