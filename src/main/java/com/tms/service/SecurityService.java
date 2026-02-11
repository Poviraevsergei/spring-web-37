package com.tms.service;

import com.tms.model.Security;
import com.tms.model.dto.RequestRegistrationDTO;
import com.tms.model.dto.UserResponse;
import com.tms.repository.SecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SecurityService {

    private final SecurityRepository securityRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public UserResponse registration(RequestRegistrationDTO registrationDTO){
        //TODO: сделать регистрацию
        log.info("Successfully registered");

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
