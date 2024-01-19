package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {
    UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id){
        User result = userDao.getUserById(id);
        if (result == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", null);
        }
        return result;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userDao.getUsers();
    }

    @RequestMapping(path = "/users/account/{id}")
    public User getUserByAccountId(@PathVariable int id) {
        User user = userDao.getUserByAccountId(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", null);
        }
        return user;
    }
}
