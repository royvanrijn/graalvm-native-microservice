package com.royvanrijn.graal;

import com.royvanrijn.graal.domain.UserRepositoryImpl;
import com.royvanrijn.graal.service.UserService;
import com.royvanrijn.graal.service.UserServiceImpl;
import dagger.Module;
import dagger.Provides;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;

@Module
public class MainModule {

    @Provides
    @Singleton
    UserService getUserService(UserRepositoryImpl repository) {
        return new UserServiceImpl(repository);
    }

    @Provides
    @Singleton
    UserRepositoryImpl userRepository(EntityManager entityManager) {
        return new UserRepositoryImpl(entityManager);
    }

    @Provides
    @Singleton
    EntityManager entityManager() {
        HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
        EntityManagerFactory entityManagerFactory = provider.createEntityManagerFactory("default", Collections.emptyMap());
        return entityManagerFactory.createEntityManager();
    }
}
