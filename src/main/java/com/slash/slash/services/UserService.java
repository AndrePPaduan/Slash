package com.slash.slash.services;

import com.slash.slash.exceptions.UserAlreadyExists;
import com.slash.slash.models.User;
import com.slash.slash.models.UserDto;

import java.util.List;

public interface UserService {

    public User addUser (User user) throws UserAlreadyExists;
    public void deleteUser(User user);
    public User editUser (String userEmail, String userPass, User user);
    public void login (String userEmail, String userPassword);
    public void sendConfirmationEmail(String userEmail);
    public void closeSession (String userEmail);
    public void changePassword (String userEmail, String userPassword, String newPassword);
    public List<UserDto> listUsers();
    public UserDto retrieveUserByName(String name, String surname);
    public UserDto userToUserDto(User user);
}
