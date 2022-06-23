package com.binar.kelompok3.secondhand.service.users;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;


    //digunakan untuk menu Lengkapi Info Akun
    @Override
    public void updateUsers(Integer id, String name, String address, String phone, String cityName) {
        usersRepository.updateUsers(id, name, address, phone, cityName);
    }

    //digunakan untuk mengubah password
    @Override
    public void updatePassword(Integer id, String password) {
        usersRepository.updatePassword(id, passwordEncoder.encode(password));
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    @Override
    public void deleteUsersById(Integer id) {
        usersRepository.deleteUsersById(id);
    }

    @Override
    public Users findUsersById(Integer id) {
        return usersRepository.findUsersById(id);
    }

    @Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

}
