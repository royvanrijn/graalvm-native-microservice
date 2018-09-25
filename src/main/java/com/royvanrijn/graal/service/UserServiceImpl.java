package com.royvanrijn.graal.service;

import com.royvanrijn.graal.domain.User;
import com.royvanrijn.graal.domain.UserRepositoryImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserServiceImpl implements UserService {

    private UserRepositoryImpl repository;

    @Inject
    public UserServiceImpl(final UserRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public User get(final String id) {
        return repository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
}
