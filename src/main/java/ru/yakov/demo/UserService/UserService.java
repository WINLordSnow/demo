package ru.yakov.demo.UserService;


import ru.yakov.demo.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDto saveUser(UserDto user);
    UserDto getUser(int id);
    void updateUser(UserDto user);
    void deleteUser(int id);
    List<UserDto> getAllUsers();
    UserDto findByLogin(String login);
}
