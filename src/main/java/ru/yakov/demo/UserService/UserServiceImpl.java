package ru.yakov.demo.UserService;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakov.demo.dto.UserDto;
import ru.yakov.demo.model.User;
import ru.yakov.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User saveUser = userRepository.save(userConverter.fromUserDtoToUser(userDto));
        return userConverter.fromUserToUserDto(saveUser);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            return userConverter.fromUserToUserDto(user);
        }
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> usersList = userRepository.findAll();
        List<UserDto> usersDtoList = new ArrayList<>();
        usersList.forEach(user -> usersDtoList.add(userConverter.fromUserToUserDto(user)));
        return usersDtoList;
    }

    @Override
    public UserDto getUser(int id) {
        return userConverter.fromUserToUserDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public void updateUser(UserDto user) {
        userRepository.save(userConverter.fromUserDtoToUser(user));
    }


}
