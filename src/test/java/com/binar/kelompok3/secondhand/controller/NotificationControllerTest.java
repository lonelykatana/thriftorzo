package com.binar.kelompok3.secondhand.controller;

import com.binar.kelompok3.secondhand.model.request.notif.ReadNotificationRequest;
import com.binar.kelompok3.secondhand.model.response.MessageResponse;
import com.binar.kelompok3.secondhand.service.UserDetailsServiceImpl;
import com.binar.kelompok3.secondhand.service.notification.INotificationService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import com.binar.kelompok3.secondhand.utils.AuthEntryPointJwt;
import com.binar.kelompok3.secondhand.utils.AuthTokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@WebMvcTest(controllers = NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserDetailsServiceImpl userDetailsService;
    @MockBean
    AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    AuthTokenFilter authTokenFilter;
    @MockBean
    private INotificationService iNotificationService;
    @MockBean
    private IUsersService usersService;


    @Test
    void readNotif() throws Exception {
        ReadNotificationRequest request = new ReadNotificationRequest(56);
        mockMvc.perform(put("/notification/read").secure(true)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }
}
