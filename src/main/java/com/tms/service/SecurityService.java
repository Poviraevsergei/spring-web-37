package com.tms.service;

import com.tms.model.Role;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.AuthRequestDto;
import com.tms.model.dto.RequestRegistrationDTO;
import com.tms.model.dto.UserResponse;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import com.tms.utils.JwtUtils;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SecurityService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public SecurityService(JwtUtils jwtUtils, SecurityRepository securityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponse registration(RequestRegistrationDTO registrationDTO){
        log.info("IN: SecurityService:registration");

        if (securityRepository.existsByUsername(registrationDTO.getUsername())
                || userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new ValidationException("Username or Email already exists");
        }

        User user = new User();
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        user.setAge(registrationDTO.getAge());
        user.setCreated(Instant.now());
        user.setUpdated(Instant.now());
        user = userRepository.save(user);

        Security security = new Security();
        security.setUsername(registrationDTO.getUsername());
        security.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        security.setCreated(Instant.now());
        security.setUpdated(Instant.now());
        security.setRole(Role.USER);
        security.setUserId(user.getId());
        security = securityRepository.save(security);
        log.info("Successfully registered: {}", security.getUsername());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setAge(user.getAge());
        log.info("OUT: SecurityService:registration");
        return userResponse;
    }

    public Optional<Security> getSecurityById(Integer id) {
        return securityRepository.findById(id);
    }

    public List<Security> getAllSecurities(){
        return securityRepository.findAll();
    }

    public boolean canAccess(Integer id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Security> optionalSecurity = securityRepository.findByUsername(username);
        if(optionalSecurity.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        Security security = optionalSecurity.get();
        return security.getUserId().equals(id) || security.getRole().equals(Role.ADMIN);
    }

    public Optional<String> generateToken(AuthRequestDto dto){
        log.info("IN: SecurityService:generateToken");
        Optional<Security> securityOptional = securityRepository.findByUsername(dto.getUsername());
        if(securityOptional.isEmpty()){
            throw new UsernameNotFoundException(dto.getUsername());
        }
        Security security = securityOptional.get();
        if (passwordEncoder.matches(dto.getPassword(), security.getPassword())){
            Optional<String> jwtOptional = Optional.ofNullable(jwtUtils.generateToken(dto.getUsername()));
            log.info("OUT: SecurityService:generateToken");
            return jwtOptional;
        }
        log.info("OUT: SecurityService:generateToken");
        return Optional.empty();
    }
}
