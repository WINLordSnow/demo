package ru.yakov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakov.demo.dto.UserDto;
import ru.yakov.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
