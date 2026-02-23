package com.tms.controller;

import com.tms.model.Security;
import com.tms.model.dto.RequestRegistrationDTO;
import com.tms.model.dto.UserResponse;
import com.tms.service.SecurityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@RestController
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
    public ResponseEntity<HttpStatus> registration(@ModelAttribute @Valid RequestRegistrationDTO registrationDTO,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                log.warn(objectError.getDefaultMessage());
            }
           return null;
        }
        UserResponse userResponse = securityService.registration(registrationDTO);
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Security> getSecurityById(@PathVariable("id") Integer id) {
        Optional<Security> securityOptional = securityService.getSecurityById(id);
        if (securityOptional.isPresent()) {
            return ResponseEntity.ok(securityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
}
