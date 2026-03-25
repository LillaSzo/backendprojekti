package com.wordapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.wordapp.domain.AppUser;
import com.wordapp.domain.AppUserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class RestAppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

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

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void deleteUserWithId() throws Exception {

        AppUser appUser = new AppUser("testuser", "$2a$12$iH2puSvtm6xisqokS/JOJOfD2ZrltJiWTksSqwwOvfYUJBPdsQ3hm", "USER");
        appUserRepository.save(appUser);

        mockMvc.perform(delete("/users/" + appUser.getId()))
            .andExpect(status().isOk());

        AppUser appUser2 = appUserRepository.findByUsername("testuser");
        assertThat(appUser2).isNull();
    }
}
