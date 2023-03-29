package com.example.demo.repository;

import com.example.demo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByPermalink(String permalink);

    @Query("SELECT DISTINCT p FROM Project p JOIN p.areas a WHERE a.name IN :areaNames ORDER BY p.id ASC")
    List<Project> findByAreaNames(@Param("areaNames") List<String> areaNames);
}
