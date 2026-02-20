package com.wordapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;

@Controller
public class PracticeController {

    private DeckRepository deckRepository;

    public PracticeController (DeckRepository deckRepository){
        this.deckRepository = deckRepository;
    }

    @GetMapping("practice/deck/{id}")
    public String viewPractice(@PathVariable ("id") Long deckid, @RequestParam(required = false, defaultValue = "false") boolean practice, Model model){
        Deck deck = deckRepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));
        model.addAttribute("deck", deck);
        model.addAttribute("cards", deck.getCardlist());
        model.addAttribute("practice", practice);

        return "practice";
    }

}
