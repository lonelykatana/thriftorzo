package com.binar.kelompok3.secondhand;

import com.binar.kelompok3.secondhand.service.cities.ICititesService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApp {

    @Autowired
    private ICititesService iCititesService;

    @Autowired
    private IUsersService iUsersService;

    @Test
    @DisplayName("Test save cities")
    void addCities(){
        Assertions.assertEquals("Sukses", iCititesService.saveCities("Jakarta"));
    }

    @Test
    @DisplayName("Test update cities")
    void updateCities(){
        Assertions.assertEquals("sukses update cities", iCititesService.updateCities("medan1",1));
    }

//    @Test
//    @DisplayName("Test save users")
//    void saveUsers(){
//        Assertions.assertEquals("sukses save users",iUsersService.saveUsers("Erick","berdikari","0813",1));
//    }
}
