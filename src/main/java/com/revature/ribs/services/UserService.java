package com.revature.ribs.services;

import com.revature.ribs.daos.UserDAO;
import com.revature.ribs.dtos.requests.NewUserRequest;
import com.revature.ribs.models.Role;
import com.revature.ribs.models.User;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public User signup(NewUserRequest req){
        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), req.getPassword1(), Role.DEFAULT);
        userDAO.save(createdUser);
        return createdUser;
    }

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    public List<User> getAllUsersByUsername(String username){
        return userDAO.getAllUsersByUserName(username);
    }

    public boolean isValidUsername(String username){
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    public boolean isDuplicateUsername(String username){
        List<String> usernames = userDAO.findAllUsernames();
        return usernames.contains(username);
    }

    public boolean isValidPassword(String password){
        return password.matches("^(?=.*[A-Za-z])(?=.*\\\\d)[A-Za-z\\\\d]{8,}$");
    }

    public boolean isSamePassword(String password1, String password2){
        return password1.equals(password2);
    }

}
