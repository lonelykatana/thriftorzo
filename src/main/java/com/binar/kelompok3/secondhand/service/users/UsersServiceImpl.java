package com.binar.kelompok3.secondhand.service.users;

import com.binar.kelompok3.secondhand.model.Cities;
import com.binar.kelompok3.secondhand.model.users.Users;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import com.binar.kelompok3.secondhand.service.cities.ICititesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

    private UsersRepository usersRepository;
    private ICititesService iCititesService;


    //digunakan untuk menu Lengkapi Info Akun
    @Override
    public void updateUsers(Integer id, String name, String address, String phone, String cityName) {
        Users users = new Users();
        users.setId(id);
        users.setName(name);
        users.setAddress(address);
        users.setPhone(phone);
        users.setCityName(cityName);
        usersRepository.save(users);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    @Override
    public String deleteUsersById(Integer id) {
        usersRepository.deleteUsersById(id);
        return "sukses delete id";
    }

    @Override
    public Users findUsersById(Integer id) {
        return usersRepository.findUsersById(id);
    }

}
