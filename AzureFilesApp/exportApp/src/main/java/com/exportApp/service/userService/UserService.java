package com.exportApp.service.userService;

import com.exportApp.config.JwtService;
import com.exportApp.mapper.AuthenticationResponse;
import com.exportApp.mapper.UserRequest;
import com.exportApp.model.entity.User;
import com.exportApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername()).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        if (Objects.equals(user.getPassword(), userRequest.getPassword())) {
            return AuthenticationResponse.builder().token(jwtToken).build();
        } else {
            return AuthenticationResponse.builder().build();
        }

    }
}
