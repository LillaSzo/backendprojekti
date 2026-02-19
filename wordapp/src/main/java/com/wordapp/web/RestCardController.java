package com.wordapp.web;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordapp.domain.Card;
import com.wordapp.domain.CardRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class RestCardController {

    private CardRepository cardRepository;
    private DeckRepository deckRepository;

    public RestCardController(CardRepository cardRepository, DeckRepository deckRepository){
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    @GetMapping("/cards")
    public Iterable<Card> findAllCards(){
        return cardRepository.findAll();
    }
    @GetMapping("cards/{id}")
    public Optional<Card> findCardById(@PathVariable("id") Long cardid){
       return cardRepository.findById(cardid); 
    }

    @PostMapping("decks/{id}/cards")
    public Card postNewCard(@PathVariable("id") Long deckid, @RequestBody Card newCard){
        Deck deck = deckRepository.findById(deckid)
            .orElseThrow(() -> new RuntimeException("Deck not found"));
        newCard.setDeck(deck);
        return cardRepository.save(newCard);
    }

    @PutMapping("cards/{id}")
    public Card updateCard(@PathVariable Long id, @RequestBody Card updatedCard) {
        cardRepository.findById(id);
        return cardRepository.save(updatedCard);
    }
    @DeleteMapping("/cards/{id}")
    public Iterable<Card> deleteCard(@PathVariable ("id") Long cardid){
        cardRepository.deleteById(cardid);
        return cardRepository.findAll();
    }
}
