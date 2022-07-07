package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.model.response.JwtResponse;
import com.binar.kelompok3.secondhand.utils.JwtUtils;
import com.binar.kelompok3.secondhand.enumeration.ERole;
import com.binar.kelompok3.secondhand.model.entity.Roles;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.auth.*;
import com.binar.kelompok3.secondhand.model.request.SigninRequest;
import com.binar.kelompok3.secondhand.model.request.SignupRequest;
import com.binar.kelompok3.secondhand.repository.RolesRepository;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    // For user authentication
    @Autowired
    AuthenticationManager authenticationManager;

    // Encoding incoming password
    @Autowired
    PasswordEncoder passwordEncoder;

    // Generate Token
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody SigninRequest request) {

        // check if user authenticated
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token for auth
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, /*type: bearear,*/ userDetails.getId(), userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> userRegister(@Valid @RequestBody SignupRequest request) {

        // Check if email used
        Boolean emailExists = usersRepository.existsByEmail(request.getEmail());
        if (Boolean.TRUE.equals(emailExists)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        // Add NAME, EMAIL, PASSWORD to db
        Users user = new Users(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        Set<Roles> roles = new HashSet<>();

        // Assigned user Roles
        for (ERole role : ERole.values()) {
            Roles mRole = rolesRepository.findByName(ERole.valueOf(role.name())).orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found"));
            roles.add(mRole);
        }

        // Save user to db
        user.setRoles(roles);
        usersRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}

// blaskoasky was here :) ganti