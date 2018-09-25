package com.royvanrijn.graal.domain;

import java.util.List;

public interface UserRepository {

    User getById(String id);

    List<User> findAll();
}
