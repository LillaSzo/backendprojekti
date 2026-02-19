package com.wordapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.wordapp.domain.Language;
import com.wordapp.domain.LanguageRepository;

@SpringBootApplication
public class WordappApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(WordappApplication.class, args);
	}

	@Bean
	public CommandLineRunner saveLanguages(LanguageRepository languageRepository){
		return (args) -> {
			Language language1 = new Language("Finnish");
			Language language2 = new Language("English");
			Language language3 = new Language("Hungarian");
			Language language4 = new Language("Swedish");
			Language language5 = new Language("French");
			Language language6 = new Language("Turkish");
			Language language7 = new Language("Spanish");

			languageRepository.save(language1);
			languageRepository.save(language2);
			languageRepository.save(language3);
			languageRepository.save(language4);
			languageRepository.save(language5);
			languageRepository.save(language6);
			languageRepository.save(language7);		
		};
	}

}
