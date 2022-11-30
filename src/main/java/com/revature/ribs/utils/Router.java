package com.revature.ribs.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ribs.daos.UserDAO;
import com.revature.ribs.handlers.UserHandler;
import com.revature.ribs.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void router(Javalin app){
        ObjectMapper mapper = new ObjectMapper();

        // User
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userHandler = new UserHandler(userService, mapper);

        //routes
        app.routes(()-> {
            //user
            path("/users", ()-> {
                get(userHandler::getAllUsers);
                get("/name", userHandler::getAllUsersByUsername);
                post(c-> userHandler.signup(c));
            });
        });
    }
}
