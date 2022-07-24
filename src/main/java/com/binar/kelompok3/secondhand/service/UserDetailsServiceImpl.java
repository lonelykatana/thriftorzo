package com.binar.kelompok3.secondhand.service;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.model.auth.UserDetailsImpl;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

   UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email);
        return UserDetailsImpl.build(user);
    }
}
