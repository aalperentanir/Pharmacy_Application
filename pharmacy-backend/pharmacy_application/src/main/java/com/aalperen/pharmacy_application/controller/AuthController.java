package com.aalperen.pharmacy_application.controller;

import com.aalperen.pharmacy_application.config.JwtProvider;
import com.aalperen.pharmacy_application.entity.User;
import com.aalperen.pharmacy_application.enums.Role;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.request.CreateUserRequest;
import com.aalperen.pharmacy_application.request.LoginRequest;
import com.aalperen.pharmacy_application.response.AuthResponse;
import com.aalperen.pharmacy_application.response.generic.RestApiResponse;
import com.aalperen.pharmacy_application.response.generic.RestResponseStatus;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import com.aalperen.pharmacy_application.service.CustomeUserDetailsService;
import com.aalperen.pharmacy_application.service.JwtService;
import com.aalperen.pharmacy_application.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RestController
@RequestMapping("/auth/v0")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth API", description = "Authentication işlemleri için API")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CustomeUserDetailsService customeUserDetailsService;
    private final JwtService jwtService;


    @PostMapping("/signup")
    @Operation(summary = "Kullanıcı kaydı", description = "Yeni bir kullanıcı oluşturur.")
    public RestApiResponse<AuthResponse> createUser(@RequestBody CreateUserRequest userRequest) throws Exception {
        User user = new User();
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setRole(userRequest.getRole());
        User createdUser = userService.createUser(user);
        String jwt = jwtService.createAuth(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("User created successfully");
        authResponse.setRole(createdUser.getRole());
        authResponse.setToken(jwt);
        return new RestApiResponse<>(RestResponseStatus.ok(), authResponse);
    }


    @PostMapping("/signin")
    @Operation(summary = "Kullanıcı login", description = "Login olma işlemi")
    public RestApiResponse<AuthResponse> signIn(@RequestBody LoginRequest req) {
        String username = req.getEmail();
        String password = req.getPassword();
        Authentication auth = authentication(username, password);

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String role = "";
        if (authorities != null) {
            role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        }
        String jwt = JwtProvider.generateToken(auth);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Signin request success");
        authResponse.setRole(Role.valueOf(role));
        authResponse.setToken(jwt);
        return new RestApiResponse<>(RestResponseStatus.ok(), authResponse);
    }

    private Authentication authentication(String username, String password) {
        try {
            UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);
            if (userDetails == null) {
                throw new BusinessException("Invalid username or password", ReturnCodes.USER_NOT_FOUND.intValue(),
                        ReturnCodes.USER_NOT_FOUND.stringValue());
            }

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BusinessException("Invalid password", ReturnCodes.USER_NOT_FOUND.intValue(),
                        ReturnCodes.USER_NOT_FOUND.stringValue());
            }
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(), ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }
}
