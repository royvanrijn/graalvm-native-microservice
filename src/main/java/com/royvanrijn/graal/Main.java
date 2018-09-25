package com.royvanrijn.graal;


import com.royvanrijn.graal.domain.User;
import com.royvanrijn.graal.service.UserService;
import com.royvanrijn.graal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;

/**
 * A simple application/microservice that does something CRUD and can be compiled into native code using GraalVM.
 *
 * Working:
 * - Web framework: sprakjava
 * - Logging: SLF4J + java.util.logging
 * - Dependency injection: Dagger 2
 * - JSON mapping: GSON
 * - Database: Hibernate/Oracle
 *
 * Not working (yet?):
 * - Xerces
 */
public class Main {

    @Inject
    UserService userService;

    private MainComponent component;

    private Main() {
        LOGGER.debug("Injecting application using Dagger 2");
        component = DaggerMainComponent.builder().build();
        component.inject(this);
    }

    static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        LOGGER.info("Starting server at {}", new Date().toString());
        new Main().startEndpoints();
	}

    private void startEndpoints() {
        LOGGER.info("Starting endpoints with Sparkjava");
        path("/user", () -> {
            // Endpoint: localhost:4567/user/ <- gets all users
            get("/", (req, res) -> {
                List<User> users = userService.getAll();
                res.status(200);
                return users;
            }, JsonUtil::toJson);
            // Endpoint: localhost:4567/user/:id <- gets a specific user
            get("/:id", (req, res) -> {
                User user = userService.get(req.params(":id"));
                res.status(200);
                return user;
            }, JsonUtil::toJson);
        });

        // global exception handler
        exception(Exception.class, (e, req, res) -> {
            LOGGER.error(String.format("%s : Got an exception for request : %s  ", e.getLocalizedMessage(), req.url()));
            LOGGER.error(e.getLocalizedMessage(), e);
            res.status(500);
            res.body("Internal Server Error");
        });
    }
}
