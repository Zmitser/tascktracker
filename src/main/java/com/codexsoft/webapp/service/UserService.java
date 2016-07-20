package com.codexsoft.webapp.service;


import com.codexsoft.webapp.model.User;

import java.util.List;

public interface UserService {

    User create(User user);
    User findOne(Long userId);
    List<User> findAll();
    void delete(Long taskId);
    void update(User user);
    User findByUsername(String username);

}
