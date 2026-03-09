package com.tms.service;

import com.tms.model.Security;
import com.tms.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final SecurityRepository securityRepository;

    @Autowired
    public CustomUserDetailService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Security> optionalSecurity = securityRepository.findByUsername(username);
        if (optionalSecurity.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        Security security = optionalSecurity.get();

        return User.builder()
                .username(security.getUsername())
                .password(security.getPassword())
                .roles(security.getRole().toString())
                .build();
    }
}
