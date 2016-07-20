package com.codexsoft.webapp.service.impl;

import com.codexsoft.webapp.model.User;
import com.codexsoft.webapp.repository.UserRepository;
import com.codexsoft.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User findOne(Long userId) {
        return repository.findOne(userId);
    }

    @Override
    public List<User> findAll() {
        return repository.findAllDevelopers();
    }

    @Override
    public void delete(Long taskId) {
        repository.delete(taskId);
    }

    @Override
    public void update(User user) {
        repository.save(user);
    }
}
