package com.revature.ribs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ribs.dtos.requests.NewUserRequest;
import com.revature.ribs.models.User;
import com.revature.ribs.services.UserService;
import com.revature.ribs.utils.custom_exceptions.InvalidAuthException;
import com.revature.ribs.utils.custom_exceptions.InvalidUserException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.io.IOException;

public class UserHandler {
    private final UserService userService;
    //private final TokenService tokenService;
    private final ObjectMapper mapper;
    private final static Logger logger = LoggerFactory.getLogger(User.class);

    public UserHandler(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        //this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public void signup(Context ctx) throws IOException {
        NewUserRequest req = mapper.readValue(ctx.req.getInputStream(), NewUserRequest.class);

        try {
            logger.info("Attempting to signup...");

            User createdUser;

            if(userService.isValidUsername(req.getUsername())){
                if(!userService.isDuplicateUsername(req.getUsername())){
                    if(userService.isValidPassword(req.getPassword1())){
                        if(userService.isSamePassword(req.getPassword1(), req.getPassword2())){
                            createdUser = userService.signup(req);
                        } else throw new InvalidUserException("Passwords does not match");

                    } else throw new InvalidUserException("Password needs to be minimum 8 characters long with a number");
                }else throw new InvalidUserException("Duplicate username!!!");
            }else throw new InvalidUserException("Username must be 8-20 characters long");
            ctx.status(201);
            ctx.json(createdUser.getId());
            logger.info("Signup attempt successful...");
        } catch (InvalidUserException e){
            ctx.status(403);
            ctx.json(e);
            logger.info("Failed Signup process...");
        }
    }

    public void getAllUsers(Context ctx){
        try {
            //token services
            //principal

            List<User> users = userService.getAllUsers();
            ctx.json(users);
        } catch (InvalidAuthException e){
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void getAllUsersByUsername(Context ctx){
        try {
            //token services
            //principal
            String username = ctx.req.getParameter("username");
            List<User> users = userService.getAllUsersByUsername(username);
            ctx.json(users);
        } catch(InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

}
