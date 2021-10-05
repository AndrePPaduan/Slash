package com.slash.slash.services;

import com.slash.slash.exceptions.NotAuthorized;
import com.slash.slash.exceptions.UserAlreadyExists;
import com.slash.slash.exceptions.UserHasNoName;
import com.slash.slash.exceptions.UserDoesNotExist;
import com.slash.slash.models.Users;
import com.slash.slash.models.UserDto;
import com.slash.slash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Users addUser(Users user) throws UserAlreadyExists, UserHasNoName {
        List<UserDto> userList = listUsers();

        if (user.getName() == null) {
            throw new UserHasNoName();
        }
        for (UserDto savedUsers : userList) {
            if (savedUsers.getName().equals(user.getName())) {
                throw new UserAlreadyExists();
            }
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String name, String password) throws UserDoesNotExist, NotAuthorized {
        Users user = retrieveRealUserByName(name);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                userRepository.delete(user);
            } else {
                throw new NotAuthorized();
            }
        } else {
            throw new UserDoesNotExist();
        }
    }

    @Override
    public Users editUser(String name, Users user) throws UserDoesNotExist, NotAuthorized, UserAlreadyExists {
        Users oldUser = retrieveRealUserByName(name);
        if (oldUser == null) {
            throw new UserDoesNotExist();
        }

        List<Users> userList = userRepository.findAll();
        for (Users savedUsers : userList) {
            if (savedUsers.getName().equals(user.getName()) && oldUser.getId() != savedUsers.getId()) {
                throw new UserAlreadyExists();
            }
        }

        if (oldUser.getPassword().equals(user.getPassword())) {
            oldUser.setEmail(user.getEmail());
            oldUser.setName(user.getName());
            userRepository.save(oldUser);

        } else {
            throw new NotAuthorized();
        }
        return oldUser;
    }


    @Override
    public void login(String userEmail, String userPassword) {

    }

    @Override
    public void sendConfirmationEmail(String userEmail) {

    }

    @Override
    public void closeSession(String userEmail) {

    }

    @Override
    public void changePassword(String userEmail, String userPassword, String newPassword) {

    }

    @Override
    public List<UserDto> listUsers() {
        List<Users> userList = userRepository.findAll();
        List<UserDto> userDtoList = new LinkedList<>();

        for (Users user : userList) {
            userDtoList.add(userToUserDto(user));
        }
        return userDtoList;
    }

    @Override
    public UserDto retrieveUserByName(String name) throws UserDoesNotExist {

        List<UserDto> userList = listUsers();

        for (UserDto userDto : userList) {
            if (userDto.getName().equals(name)) return userDto;
        }
        throw new UserDoesNotExist();

    }

    private UserDto userToUserDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    @Override
    public Users retrieveRealUserByName(String name) throws UserDoesNotExist {

        List<Users> userList = userRepository.findAll();

        for (Users user : userList) {
            if (user.getName().equals(name)) return user;
        }
        throw new UserDoesNotExist();

    }
}
