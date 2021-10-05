package com.slash.slash.controllers;

import com.slash.slash.exceptions.*;
import com.slash.slash.models.Users;
import com.slash.slash.models.UserDto;
import com.slash.slash.security.UserPrincipalDetailsService;
import com.slash.slash.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(String name, String password) throws UserDoesNotExist, NotAuthorized {
          userService.deleteUser(name, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/{name}/")
    public ResponseEntity<?> editUser(@PathVariable String name, Users user) throws NotAuthorized, UserDoesNotExist, UserAlreadyExists {
       Users editedUser = userService.editUser(name, user);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    @GetMapping({"/login"})
    public ResponseEntity<?> login(Authentication authenticatedUser) throws UserRoleNotFoundException, UserDoesNotExist {
        switch (UserPrincipalDetailsService.userIs(authenticatedUser)) {
            case "ADMIN":
                return new ResponseEntity<>("\"ADMIN\"", HttpStatus.OK);
            case "USER":
                break;
            default:
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Users user = userService.retrieveRealUserByName(authenticatedUser.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
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

    @GetMapping("/user/{name}")
    public ResponseEntity<?> retrieveUserByName(@PathVariable String name) throws UserDoesNotExist {
        UserDto userDto = userService.retrieveUserByName(name);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
