package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.service.UserDetailsServiceImpl;
import com.binar.kelompok3.secondhand.service.imageproduct.IImageProductService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import com.binar.kelompok3.secondhand.utils.AuthEntryPointJwt;
import com.binar.kelompok3.secondhand.utils.AuthTokenFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PublicController.class)
class PublicControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserDetailsServiceImpl userDetailsService;
    @MockBean
    AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    AuthTokenFilter authTokenFilter;
    @MockBean
    IProductsService iProductsService;
    @MockBean
    IImageProductService productService;
    @MockBean
    IUsersService usersService;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(get("/public/all").secure(true)
                        .contentType("application/json"))
                .andExpect(status().isOk());

    }
}
