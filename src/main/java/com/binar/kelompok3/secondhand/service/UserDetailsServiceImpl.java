package com.binar.kelompok3.secondhand.service;

import com.binar.kelompok3.secondhand.model.auth.UserDetailsImpl;
import com.binar.kelompok3.secondhand.model.users.Users;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email);
        return UserDetailsImpl.build(user);
    }
}
