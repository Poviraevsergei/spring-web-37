package com.tms.service;

import com.tms.model.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public UserResponse registration(
            String username,
            String password,
            String firstName,
            String lastName,
            String email,
            Integer age
    ){
        System.out.println(username);
        System.out.println(password);
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(email);
        System.out.println(age);
        System.out.println("Successfully registered"); //TODO: сделать регистрацию

        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(firstName);
        userResponse.setLastName(lastName);
        userResponse.setEmail(email);
        userResponse.setAge(age);
        return userResponse;
    }
}
