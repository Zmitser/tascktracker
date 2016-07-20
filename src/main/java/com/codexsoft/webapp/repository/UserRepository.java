package com.codexsoft.webapp.repository;


import com.codexsoft.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.role = com.codexsoft.webapp.model.Role.ROLE_DEVELOPER")
    List<User> findAllDevelopers();
}
