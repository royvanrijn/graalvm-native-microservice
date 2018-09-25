package com.royvanrijn.graal.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final EntityManager entityManager;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getById(final String id) {
        try {
            final Query query = entityManager.createQuery("FROM User WHERE id LIKE :id");
            query.setParameter("id", id);
            return (User) query.getSingleResult();
        } catch (RuntimeException e) {
            LOGGER.error("failed to find user with id {} :{}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        try {
            final Query query = entityManager.createQuery("FROM User");
            return query.getResultList();
        } catch (RuntimeException e) {
            LOGGER.error("failed to find users:{}", e.getMessage());
            return null;
        }
    }
}
