package com.wordapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RestAppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testGetAllUsers() throws Exception {

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testGetUserWithId() throws Exception {

        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testGetUserWithRole() throws Exception {

        mockMvc.perform(get("/users/role?role=ADMIN"))
            .andExpect(status().isOk());
    }

}
