package com.wordapp.web;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.wordapp.domain.Card;
import com.wordapp.domain.CardRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;

@Controller
public class CardController {

    private CardRepository crepository;
    private DeckRepository drepository;

    public CardController(CardRepository crepository, DeckRepository drepository){
        this.crepository = crepository;
        this.drepository = drepository;
    }

    @GetMapping("/main")
    public String showMain(Model model){
        model.addAttribute("decks", drepository.findAll());
        return "main";
    }

    @GetMapping("/create")
    public String createDeck(Model model){
        model.addAttribute("deck", new Deck());
        return "createdeck";
    }
    @PostMapping("/savedeck")
    public String saveDeck(Deck deck){
        drepository.save(deck);
        return "redirect:/main";
    }

    @GetMapping("/delete/{id}")
    public String deleteDeck(@PathVariable("id") Long deckid){
        drepository.deleteById(deckid);
        return "redirect:/main";
    }

    @GetMapping("/edit/{id}")
    public String pickEdit(@PathVariable("id") Long deckid, Model model){
        Deck deck = drepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));
        model.addAttribute("deck", deck);
        model.addAttribute("cards", deck.getCardlist());
        return "editdeck";
    }

    @PostMapping("/edit/{id}")
    public String editDeck(@PathVariable("id") Long deckid, Deck deck){
        drepository.save(deck);
        return "redirect:/main";
    }

    @GetMapping("/deck/{id}/addwords")
    public String createCard(@PathVariable("id") Long deckid, Model model){
        Deck deck = drepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));

        Card card = new Card();
        card.setDeck(deck);

        model.addAttribute("deck", deck);
        model.addAttribute("cards", card);
        return "addwords";
    }

    @PostMapping("/deck/{id}/addwords")
    public String saveWords(@PathVariable("id") Long deckid, Card card){
        Deck deck = drepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));
        card.setDeck(deck);
        crepository.save(card);
        return "redirect:/main";
    }

}
