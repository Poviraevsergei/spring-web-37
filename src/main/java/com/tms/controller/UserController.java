package com.tms.controller;

import com.tms.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        //Temp
        User user = new User();
        user.setId(id);
        user.setAge(22);
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEmail("john.smith@gmail.com");
        user.setCreated(Instant.now());
        user.setUpdated(Instant.now());
        user.setActive(false);

        model.addAttribute("user", user);
        return "get-user";
    }
}
