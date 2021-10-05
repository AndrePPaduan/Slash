package com.slash.slash.services;

import com.slash.slash.exceptions.NotAuthorized;
import com.slash.slash.exceptions.UserAlreadyExists;
import com.slash.slash.exceptions.UserHasNoName;
import com.slash.slash.exceptions.UserDoesNotExist;
import com.slash.slash.models.Users;
import com.slash.slash.models.UserDto;

import java.util.List;

public interface UserService {

    public Users addUser (Users user) throws UserAlreadyExists, UserHasNoName;
    public void deleteUser(String name, String password) throws UserDoesNotExist, NotAuthorized;
    public Users editUser (String name, Users user) throws UserDoesNotExist, NotAuthorized, UserAlreadyExists;
    public void login (String userEmail, String userPassword);
    public void sendConfirmationEmail(String userEmail);
    public void closeSession (String userEmail);
    public void changePassword (String userEmail, String userPassword, String newPassword);
    public List<UserDto> listUsers();
    public UserDto retrieveUserByName(String name) throws UserDoesNotExist;
    public Users retrieveRealUserByName(String name) throws UserDoesNotExist;
}
