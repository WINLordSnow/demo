package ru.yakov.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.yakov.demo.UserService.UserService;
import ru.yakov.demo.model.Role;
import ru.yakov.demo.model.User;
import ru.yakov.demo.repository.RoleRepository;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/")
public class UserRestController {
    private final UserService userService;
    private final Set<Role> allRoles;

    public UserRestController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.save(new Role("USER"));
            roleRepository.save(new Role("ADMIN"));
        }
        this.allRoles = new HashSet<>(roleRepository.findAll());
    }

    @GetMapping("/roles")
    public Set<Role> getAllRoles() {
        return allRoles;
    }

    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/user")
    public User getUser(Principal user) {
        return userService.findByLogin(user.getName());
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> list = userService.getAllUsers();
        list.sort(Comparator.comparingInt(User::getId));
        return list;
    }

    @PostMapping("/edit")
    public void editUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody String id) {
        System.out.println("i`m here");
        System.out.println(id);
        userService.deleteUser(Integer.parseInt(id));
    }
}
