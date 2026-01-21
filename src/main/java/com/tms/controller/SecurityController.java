package com.tms.controller;

import com.tms.model.dto.UserResponse;
import com.tms.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//CRUD + logic methods
@Controller
@RequestMapping("/security") //путь перед всеми методами контроллера
public class SecurityController {
    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registration(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam(value = "age") Integer age,
            ModelAndView modelAndView
    ) {
        UserResponse userResponse = securityService.registration(username, password, firstName, lastName, email, age);
        modelAndView.addObject("first_name", userResponse.getFirstName());
        modelAndView.addObject("last_name", userResponse.getLastName());
        modelAndView.addObject("email", userResponse.getEmail());
        modelAndView.addObject("age", userResponse.getAge());
        modelAndView.setViewName("registration-response");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public String getSecurityById(@PathVariable("id") Long id) {
        System.out.println("getSecurityById method in SecurityController. Id: " + id);
        return "registration-response";
    }


    //Знать:
    //1. схему Spring MVC
    //2. Как настраивать MVC
    //3. разница @PathVariable @RequestParam
    //4. Что такое Model и ModelAndView
    //5. Что такое Interceptor
}
