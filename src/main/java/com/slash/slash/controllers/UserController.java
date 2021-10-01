package com.slash.slash.controllers;

import com.slash.slash.exceptions.UserAlreadyExists;
import com.slash.slash.models.User;
import com.slash.slash.models.UserDto;
import com.slash.slash.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(User user) throws UserAlreadyExists {
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(User user) {
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editUser(String userEmail, String userPass, User user) {
        return null;
    }


    public ResponseEntity<?> login(String userEmail, String userPassword) {
        return null;
    }

    public ResponseEntity<?> sendConfirmationEmail(String userEmail) {
        return null;
    }

    public ResponseEntity<?> closeSession(String userEmail) {
        return null;
    }

    public ResponseEntity<?> changePassword(String userEmail, String userPassword, String newPassword) {
        return null;
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listUsers() {
        List<UserDto> userList = userService.listUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping(value = "/user/name/{name}/{surname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retrieveUserByName(String name, String surname) {
        UserDto userDto = userService.retrieveUserByName(name, surname);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
