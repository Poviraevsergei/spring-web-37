package com.tms.service;

import com.tms.model.Security;
import com.tms.model.dto.RequestRegistrationDTO;
import com.tms.model.dto.UserResponse;
import com.tms.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {

    private final SecurityRepository securityRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public UserResponse registration(RequestRegistrationDTO registrationDTO){

        System.out.println(registrationDTO.getUsername());
        System.out.println(registrationDTO.getPassword());
        System.out.println(registrationDTO.getFirstName());
        System.out.println(registrationDTO.getLastName());
        System.out.println(registrationDTO.getEmail());
        System.out.println(registrationDTO.getAge());
        System.out.println("Successfully registered"); //TODO: сделать регистрацию

        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(registrationDTO.getFirstName());
        userResponse.setLastName(registrationDTO.getLastName());
        userResponse.setEmail(registrationDTO.getEmail());
        userResponse.setAge(userResponse.getAge());
        return userResponse;
    }

    public List<Security> getAllSecurities(){
        return securityRepository.getAllSecurities();
    }
}
