package com.wordapp.web;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordapp.domain.Language;
import com.wordapp.domain.LanguageRepository;

@RestController
public class RestLanguageController {

    private final LanguageRepository languageRepository;

    public RestLanguageController(LanguageRepository languageRepository){
        this.languageRepository = languageRepository;
    }

    @GetMapping("/languages")
    public Iterable<Language> findAllLanguages(){
        return languageRepository.findAll();
    }

    @GetMapping("/languages/{id}")
    public Optional<Language> findByLanguageId(@PathVariable("id") Long languageid){
        return languageRepository.findById(languageid);
    }

    @PostMapping("/language")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Language postNewLanguage(@RequestBody Language newLanguage){
        return languageRepository.save(newLanguage);
    }
    @PutMapping("language/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Language updateLanguage(@PathVariable("id") Long languageid, @RequestBody Language updatedLanguage){
        languageRepository.findById(languageid);
        return languageRepository.save(updatedLanguage);
    }

    @DeleteMapping("language/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Iterable<Language> deleteLanguage(@PathVariable("id") Long languageid){
       languageRepository.deleteById(languageid);
       return languageRepository.findAll(); 
    }

}
