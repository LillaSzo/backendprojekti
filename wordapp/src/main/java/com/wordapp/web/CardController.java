package com.wordapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    @PostMapping("/save")
    public String saveCard(Deck deck){
        drepository.save(deck);
        return "redirect:/main";
    }

    @GetMapping("/delete/{id}")
    public String deleteDeck(@PathVariable("id") Long deckid, Model model){
        drepository.deleteById(deckid);
        return "redirect:/main";
    }
}
