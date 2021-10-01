package com.slash.slash.services;

import com.slash.slash.exceptions.UserAlreadyExists;
import com.slash.slash.models.User;
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
    public User addUser(User user) throws UserAlreadyExists {
        List<UserDto> userList = listUsers();
        for (UserDto savedUsers : userList) {
            if (savedUsers.getName().equals(user.getName()) && savedUsers.getSurname().equals(user.getSurname())) {
                throw new UserAlreadyExists();
            }
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        if (user != null) {
            userRepository.delete(user);
        } else {
            System.out.println("User does not exist");
        }
    }

    @Override
    public User editUser(String userEmail, String userPass, User user) {
        return null;
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
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new LinkedList<>();

        for (User user : userList) {
            userDtoList.add(userToUserDto(user));
        }
        return userDtoList;
    }

    @Override
    public UserDto retrieveUserByName(String name, String surname) {

        List<UserDto> userList = listUsers();

        for (UserDto userDto : userList) {
            if (userDto.getName().equals(name) && userDto.getSurname().equals(surname)) return userDto;
        }
        return null;

    }

    @Override
    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

}
