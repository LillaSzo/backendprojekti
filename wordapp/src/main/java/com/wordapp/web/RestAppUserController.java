package com.wordapp.web;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordapp.domain.AppUser;
import com.wordapp.domain.AppUserRepository;

@RestController
@PreAuthorize("hasAuthority('ADMIN')") 
public class RestAppUserController {

    private final AppUserRepository appUserRepository;

    public RestAppUserController(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/users")
    public Iterable<AppUser> findAllUsers(){
        return appUserRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Optional<AppUser> findUserById(@PathVariable("id") Long id){
        return appUserRepository.findById(id);
    }

    @GetMapping("/users/role")
    public AppUser findByRole(@RequestParam String role){
        return appUserRepository.findByRole(role);
    }

    @DeleteMapping("/users/{id}")
    public Iterable<AppUser> deleteById(@PathVariable("id") Long id){
        appUserRepository.deleteById(id);
        return appUserRepository.findAll();
    }
}
