package com.tms.controller;

import com.tms.model.dto.RequestRegistrationDTO;
import com.tms.model.dto.UserResponse;
import com.tms.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/security")
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
    public ModelAndView registration(@ModelAttribute @Valid RequestRegistrationDTO registrationDTO,
                                     BindingResult bindingResult,
                                     ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                System.out.println(objectError.getDefaultMessage());
            }
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.setViewName("error");
            return modelAndView;
        }

        UserResponse userResponse = securityService.registration(registrationDTO);
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


    //Валидация:
    //1. расставить аннотации в DTO
    //2. Поставить @Valid
    //3. BindingResult bindingResult
    //4. @EnableWebMvc

    //1. ExceptionHandler на уровне контроллера(не круто)
    //@ExceptionHandler(value = {SecurityNotFound.class, Exception.class})
    //public ModelAndView exceptionHandler(Exception e) {
        //    ModelAndView modelAndView = new ModelAndView();
        //    System.out.println("exceptionHandler method in SecurityController. Exception: " + e);
        //    modelAndView.addObject("errors", e.getMessage());
        //    modelAndView.setViewName("error");
        //    return modelAndView;
        //}
}
