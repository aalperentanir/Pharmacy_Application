package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.config.JwtProvider;
import com.aalperen.pharmacy_application.entity.User;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.UserRepository;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import com.aalperen.pharmacy_application.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public User findUserByToken(String jwt)  {
        try {
            String email = JwtProvider.getEmailFromToken(jwt);
            return userRepository.findByEmail(email);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new BusinessException("User not found", ReturnCodes.USER_NOT_FOUND.intValue(), "User not found");
            }
            return user;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }

    }

    @Override
    public User createUser(User user)  {
        try {
            User isEmailExists = userRepository.findByEmail(user.getEmail());
            if (isEmailExists != null) {
                throw new BusinessException("Email already exist", ReturnCodes.EMAIL_ALREADY_EXISTS.intValue(),
                        "Email already exist");
            }

            User createdUser = new User();
            createdUser.setEmail(user.getEmail());
            createdUser.setFirstName(user.getFirstName());
            createdUser.setLastName(user.getLastName());
            createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
            createdUser.setRole(user.getRole());
            return userRepository.save(createdUser);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }

    }
}
