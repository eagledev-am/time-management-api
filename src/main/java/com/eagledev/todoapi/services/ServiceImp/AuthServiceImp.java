package com.eagledev.todoapi.services.ServiceImp;

import com.eagledev.todoapi.entities.User;
import com.eagledev.todoapi.models.AuthRequest;
import com.eagledev.todoapi.models.JwtResponse;
import com.eagledev.todoapi.models.UserDtoRequest;
import com.eagledev.todoapi.repos.UserRepo;
import com.eagledev.todoapi.security.JwtService;
import com.eagledev.todoapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImp(UserRepo repo , JwtService jwtService , PasswordEncoder passwordEncoder ,  AuthenticationManager authenticationManager){
        this.userRepo = repo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public JwtResponse registerUser(UserDtoRequest userDtoRequest) {
        User user = User.builder()
                .userName(userDtoRequest.getUserName())
                .password(passwordEncoder.encode(userDtoRequest.getPassword()))
                .role(userDtoRequest.getRole())
                .build();

        userRepo.save(user);
        String token = jwtService.generateToken(user);

        return JwtResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public JwtResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername() , request.getPassword())
        );

        User user = userRepo.findUserByUserName(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("USER IS NOT FOUND"));

        return JwtResponse
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
