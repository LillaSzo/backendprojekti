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
	public CommandLineRunner saveLanguages(LanguageRepository lrepository){
		return (args) -> {
			Language language1 = new Language("Finnish");
			Language language2 = new Language("English");
			Language language3 = new Language("Hungarian");
			Language language4 = new Language("Swedish");

			lrepository.save(language1);
			lrepository.save(language2);
			lrepository.save(language3);
			lrepository.save(language4);
		};
	}

}
