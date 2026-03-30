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

import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;

import jakarta.validation.Valid;

@RestController
public class RestDeckController {

    private  final DeckRepository deckRepository;

    public RestDeckController(DeckRepository deckRepository){
        this.deckRepository = deckRepository;
    }

    @GetMapping("/decks")
    public Iterable<Deck> findAllDeck(){
        return deckRepository.findAll();
    }

    @PostMapping("/deck")
    public Deck postNewDeck(@Valid @RequestBody Deck newDeck){
        return deckRepository.save(newDeck);
    }

    @GetMapping("/decks/{id}")
    public Optional<Deck> findDeckById(@PathVariable("id") Long deckid){
        return deckRepository.findById(deckid); 
    }

    @PutMapping("decks/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @deckRepository.findById(#deckid).get().user.username == authentication.name")
    public Deck updateDeck(@PathVariable("id") Long deckid,@Valid @RequestBody Deck updatedDeck){
    deckRepository.findById(deckid);
    return deckRepository.save(updatedDeck);
    }

    @DeleteMapping("decks/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @deckRepository.findById(#deckid).get().user.username == authentication.name")
    public Iterable<Deck> deleteDeck(@PathVariable("id") Long deckid){
        deckRepository.deleteById(deckid);
        return deckRepository.findAll();
    }
}
