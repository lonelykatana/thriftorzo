package com.binar.kelompok3.secondhand.service.users;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.ProductsRepository;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

    private UsersRepository usersRepository;
    private ProductsRepository productsRepository;
    private PasswordEncoder passwordEncoder;

    public UsersServiceImpl(){}


    //digunakan untuk menu Lengkapi Info Akun
    @Override
    @CachePut
    public void updateUsers(Integer id, String name, String address, String phone, String cityName, String imgUrl) {
        usersRepository.updateUsers(id, name, address, phone, cityName, imgUrl);
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
