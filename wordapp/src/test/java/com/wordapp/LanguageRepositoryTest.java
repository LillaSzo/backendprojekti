package com.wordapp;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wordapp.domain.Language;
import com.wordapp.domain.LanguageRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WordappApplication.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LanguageRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    public void findByName(){
        List<Language> languages = languageRepository.findByName("Finnish");
        assertThat(languages).hasSize(1);
        assertThat(languages.get(0).getId()).isEqualTo(1);
    }

    @Test
    public void createNewLanguage(){
        Language language = new Language("Japanese");
        languageRepository.save(language);
        assertThat(language.getId()).isNotNull();
    }
    
    @Test
    public void deleteLanguage(){
        List<Language> languages = languageRepository.findByName("Japanese");
        Language language = languages.get(0);
        languageRepository.delete(language);
        List<Language> newLanguages = languageRepository.findByName("Japanese");
        assertThat(newLanguages).hasSize(0);
    }

}
