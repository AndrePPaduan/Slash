package com.slash.slash.controllers;

import com.slash.slash.exceptions.NotAuthorized;
import com.slash.slash.exceptions.UserAlreadyExists;
import com.slash.slash.exceptions.UserHasNoName;
import com.slash.slash.exceptions.UserDoesNotExist;
import com.slash.slash.models.Users;
import com.slash.slash.models.UserDto;
import com.slash.slash.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> addUser(Users user) throws UserAlreadyExists, UserHasNoName {
        Users createdUSer = userService.addUser(user);
        return new ResponseEntity<>(createdUSer, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{name}/{surname}/{password}")
    public ResponseEntity<?> deleteUser(String name, String surname, String password) throws UserDoesNotExist, NotAuthorized {
        userService.deleteUser(name, surname, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<?> editUser(String name, String surname, String password, Users user) throws NotAuthorized, UserDoesNotExist, UserAlreadyExists {
       Users editedUser = userService.editUser(name, surname, password, user);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
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

    @GetMapping("/user")
    public ResponseEntity<?> listUsers() {
        List<UserDto> userList = userService.listUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/user/name/{name}/{surname}")
    public ResponseEntity<?> retrieveUserByName(String name, String surname) {
        UserDto userDto = userService.retrieveUserByName(name, surname);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
