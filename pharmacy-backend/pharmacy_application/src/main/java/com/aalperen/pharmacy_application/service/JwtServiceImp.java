package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.config.JwtProvider;
import com.aalperen.pharmacy_application.entity.User;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImp implements JwtService {


    @Override
    public String createAuth(User user) {
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return JwtProvider.generateToken(auth);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }
}
