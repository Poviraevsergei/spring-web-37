package com.tms.controller;

import com.tms.model.Security;
import com.tms.model.dto.AuthRequestDto;
import com.tms.model.dto.AuthResponseDto;
import com.tms.model.dto.RequestRegistrationDTO;
import com.tms.model.dto.UserResponse;
import com.tms.service.SecurityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@SecurityRequirement(name = "Bearer Authentication")
@Slf4j
@RestController
@RequestMapping("/security")
public class SecurityController {
    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registration(@Valid @RequestBody RequestRegistrationDTO registrationDTO,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                log.warn(objectError.getDefaultMessage());
            }
            throw new ValidationException();
        }
        UserResponse userResponse = securityService.registration(registrationDTO);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Security> getSecurityById(@PathVariable("id") Integer id) {
        Optional<Security> securityOptional = securityService.getSecurityById(id);
        if (securityOptional.isPresent()) {
            return ResponseEntity.ok(securityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/generate")
    public ResponseEntity<AuthResponseDto> generateJwt(@Valid @RequestBody AuthRequestDto authRequestDto) {
        Optional<String> token = securityService.generateToken(authRequestDto);
        if (token.isPresent()) {
            return new ResponseEntity<>(new AuthResponseDto(token.get()), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
