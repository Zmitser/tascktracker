package com.codexsoft.webapp.repository;


import com.codexsoft.webapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {


//    @Query("SELECT p FROM Project p WHERE p.user.userId=:userId")
//    List<Project> findAllByUserId(@Param("userId")Long userId);
}
