package com.royvanrijn.graal.service;

import com.royvanrijn.graal.domain.User;

import java.util.List;

public interface UserService {

    User get(String id);

    List<User> getAll();
}
