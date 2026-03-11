package com.tms.filter;

import com.tms.model.Security;
import com.tms.repository.SecurityRepository;
import com.tms.service.CustomUserDetailService;
import com.tms.utils.JwtUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class JwtFilter implements Filter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailService userDetailService;

    @Autowired
    public JwtFilter(JwtUtils jwtUtils, CustomUserDetailService userDetailService) {
        this.jwtUtils = jwtUtils;
        this.userDetailService = userDetailService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("IN JwtFilter: doFilter");
        Optional<String> token = jwtUtils.getTokenFromHttpRequest(servletRequest);
        if (token.isPresent()) { //TODO: добавить обработку
            String usernameFromJwt = jwtUtils.getUsernameFromToken(token.get());
            UserDetails userDetails = userDetailService.loadUserByUsername(usernameFromJwt);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Authentication successful for {}", usernameFromJwt);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("OUT JwtFilter: doFilter");
    }
}
