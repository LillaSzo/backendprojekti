package com.wordapp;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wordapp.domain.AppUserRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;
import com.wordapp.domain.LanguageRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WordappApplication.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeckRepositoryTest {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private AppUserRepository appUseRepository;

    @Test
    public void findByName(){
        List<Deck> decks = deckRepository.findByName("Testin Test Deck 2");
        assertThat(decks).hasSize(1);
        assertThat(decks.get(0).getDeckid()).isEqualTo(2);
    }

    @Test
    public void createNewDeck(){

        Deck deck = new Deck("Create test deck", languageRepository.findByName("Finnish").get(0), languageRepository.findByName("English").get(0), appUseRepository.findByUsername("user"));
        deckRepository.save(deck);
        assertThat(deck.getDeckid()).isNotNull();
    }

    @Test
    public void deleteDeck(){
        List<Deck> decks = deckRepository.findByName("Create test deck");
        Deck deck = decks.get(0);
        deckRepository.delete(deck);
        List<Deck> newDecks = deckRepository.findByName("Create test deck");
        assertThat(newDecks).hasSize(0);
    }

}
