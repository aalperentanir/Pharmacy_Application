package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.User;
import com.aalperen.pharmacy_application.enums.Role;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.UserRepository;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }

            Role role = user.getRole();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    grantedAuthorities);
        } catch (Exception e) {
            log.error("Kullanıcı yükleme hatası: {}", e.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), e.getMessage());
        }
    }
}
