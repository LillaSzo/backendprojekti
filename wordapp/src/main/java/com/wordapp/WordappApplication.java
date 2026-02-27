package com.wordapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.wordapp.domain.Card;
import com.wordapp.domain.CardRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;
import com.wordapp.domain.Language;
import com.wordapp.domain.LanguageRepository;
import com.wordapp.domain.AppUser;
import com.wordapp.domain.AppUserRepository;

@SpringBootApplication
public class WordappApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(WordappApplication.class, args);
	}

	@Bean
	public CommandLineRunner saveToDB(LanguageRepository languageRepository, DeckRepository deckRepository, CardRepository cardRepository, AppUserRepository appUserRepository){
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
			
			Deck deck = new Deck("Test deck 1", language1, language2, 0);
			 deckRepository.save(deck);

			Card card1 = new Card("kissa", "cat", "Heikki on paras kissa maailmassa", deck);
			Card card2 = new Card("nuolinotaatio", "arrow notation", "Funktiota määritellään nuolinotaatiolla", deck);
			cardRepository.save(card1);
			cardRepository.save(card2);

			AppUser user1 = new AppUser("user", "$2a$10$1cJNYnutdH2IGd0Dhvhd5.Mqc1a38Bqj4ayuNK/CdQpyIRE.axyKW", "USER");
			AppUser user2 = new AppUser("admin", "$2a$10$yYvXB2c7D3TL7oOX6hO/auIMQtm1sCWmXiWSEzhbSfhV5/uMDJzf.", "ADMIN");

			appUserRepository.save(user1);
			appUserRepository.save(user2);
		};
	}

}
