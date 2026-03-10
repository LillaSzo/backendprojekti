package com.wordapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.wordapp.domain.AppUser;
import com.wordapp.domain.AppUserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WordappApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void findByUsername(){
        AppUser appUser = appUserRepository.findByUsername("user");
        assertThat(appUser).isNotNull();
        assertThat(appUser.getUsername()).isEqualTo("user");
    }

    @Test
    public void createNewAppUser(){
        AppUser newUser = new AppUser("newUser", "$2a$12$3lOA4Adga5uGKoMEeKX2e.xzHBvRs4S8h/4E5RhKHr9JNZNqW4Mbe", "USER");
        appUserRepository.save(newUser);
        assertThat(newUser.getId()).isNotNull();
    }

    @Test
    public void deleteUser(){
        AppUser appUser = appUserRepository.findByUsername("newUser");
        appUserRepository.delete(appUser);
        assertThat(appUserRepository.findByUsername("newUser")).isNull();
    }

}
