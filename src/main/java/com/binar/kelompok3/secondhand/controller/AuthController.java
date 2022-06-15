package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.config.JwtUtils;
import com.binar.kelompok3.secondhand.model.Users;
import com.binar.kelompok3.secondhand.model.auth.*;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid
                                                        @RequestBody SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail()));
    }

    @PostMapping("/signup")

    public ResponseEntity<MessageResponse> userRegister(@Valid @RequestBody SignupRequest request) {

        Boolean emailExists = usersRepository.existsByEmail(request.getEmail());

        if (Boolean.TRUE.equals(emailExists)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        Users user = new Users(request.getName(), request.getEmail(), request.getPassword()); // Nanti password pake encoder

        usersRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}