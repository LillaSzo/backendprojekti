package com.wordapp;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.wordapp.domain.Card;
import com.wordapp.domain.CardRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WordappApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Test
    public void findByTargetWord(){
        List<Card> cards = cardRepository.findByTargetWord("kissa");
        assertThat(cards).hasSize(1);
        assertThat(cards.get(0).getTranslation()).isEqualTo("cat");
    }

    @Test
    public void createNewWord(){
        List<Deck> decks = deckRepository.findByName("User Test Deck");
        Deck deck = decks.get(0);

        Card card = new Card("testi", "test", "Kortin luonti JPA test", deck);
        cardRepository.save(card);
        assertThat(card.getCardid()).isNotNull();
    }

    @Test
    public void deleteWord(){
        List<Card> cards = cardRepository.findByTargetWord("testi");
        Card card = cards.get(0);
        cardRepository.delete(card);
        List<Card> newCards = cardRepository.findByTargetWord("testi");
        assertThat(newCards).hasSize(0);
    }

}
