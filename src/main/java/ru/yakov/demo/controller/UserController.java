package ru.yakov.demo.controller;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.yakov.demo.UserService.UserService;
import ru.yakov.demo.dto.UserDto;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    //private final Set<Role> allRoles;

    public UserController(@Qualifier("userService") UserService userService) {
        this.userService = userService;
      //  this.allRoles = allRoles;
    }

//    @RequestMapping(value = "login", method = RequestMethod.GET)
//    public String loginPage() {
//        return "login";
//    }

    @GetMapping
    public String startPage() {
        return "header";
    }

    @GetMapping("/admin")
    public String listUsers(ModelMap model) {
        List<UserDto> list = userService.getAllUsers();
       // Set<Role> roles = userService.getAllRoles();
        model.addAttribute("users", list);
       // model.addAttribute("roles", roles);
        return "admin";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUserForm(@PathVariable("id") int id, ModelMap model) {
        UserDto user = userService.getUser(id);
        model.addAttribute("user", user);
       // model.addAttribute("allRoles", allRoles);
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(UserDto user) {
        //Set<Role> temp = new HashSet<>();
//        user.getRoles().forEach(role -> temp.add(userService.getRoleByName(role.getName())));
//        user.setRoles(temp);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @RolesAllowed(value = "ROLE_ADMIN")
    @GetMapping("/addUser")
    public String addUserForm(@ModelAttribute UserDto user, ModelMap model) {
        //model.addAttribute("allRoles", allRoles);
        model.addAttribute("user", user);
        return "addUser";
    }

    @RolesAllowed(value = "ROLE_ADMIN")
    @PostMapping("/addUser")
    public String addUser(UserDto user) {
//        Set<Role> temp = new HashSet<>();
//        if (user.getRoles().isEmpty()) {
//            user.addRole(new Role("USER"));
//        }
//        allRoles.stream().filter(role -> user.getRoles().contains(new Role(role.getName()))).forEach(temp::add);
//        user.setRoles(temp);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String showUser(/*Principal*/UserDto user, ModelMap modelMap) {
        UserDto userBd = userService.findByLogin(user.getName());
        modelMap.addAttribute("user", userBd);
        return "/user";
    }

}
