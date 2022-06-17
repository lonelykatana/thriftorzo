package com.binar.kelompok3.secondhand.service.users;

import com.binar.kelompok3.secondhand.model.Cities;
import com.binar.kelompok3.secondhand.model.Users;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import com.binar.kelompok3.secondhand.service.cities.ICititesService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

    private UsersRepository usersRepository;
    private ICititesService iCititesService;
    private PasswordEncoder passwordEncoder;


    //digunakan untuk menu Lengkapi Info Akun
    @Override
    public Integer updateUsers(Integer id, String name, String address, String phone, String cityName) {
        usersRepository.updateUsers(id,name,address,phone,cityName);
        return 1; //1 sukses
    }

    //digunakan untuk mengubah password
    @Override
    public Integer updatePassword(Integer id, String password) {
        usersRepository.updatePassword(id, passwordEncoder.encode(password));
        return 1; //1 sukses
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    @Override
    public String deleteUsersById(Integer id) {
        usersRepository.deleteUsersById(id);
        return "sukses delete user";
    }

    @Override
    public Users findUsersById(Integer id) {
        return usersRepository.findUsersById(id);
    }

}
