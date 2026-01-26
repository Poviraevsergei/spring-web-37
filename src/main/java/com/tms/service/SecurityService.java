package com.tms.service;

import com.tms.model.dto.RequestRegistrationDTO;
import com.tms.model.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

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
}
