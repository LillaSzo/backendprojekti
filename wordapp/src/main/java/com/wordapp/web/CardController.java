package com.wordapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wordapp.domain.Card;
import com.wordapp.domain.CardRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;

import jakarta.validation.Valid;

@Controller
public class CardController {

    private CardRepository cardRepository;
    private DeckRepository deckRepository;

    public CardController(CardRepository cardRepository, DeckRepository deckRepository){
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    @GetMapping("/deck/{id}/addword")
    public String createCard(@PathVariable("id") Long deckid, Model model, Card card){
        Deck deck = deckRepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));

        card.setDeck(deck);

        model.addAttribute("deck", deck);
        model.addAttribute("card", card);
        return "addword";
    }

    @PostMapping("/deck/{id}/addword")
    public String saveCard(@PathVariable("id") Long deckid, @Valid Card card, BindingResult bindingResult, Model model){

        Deck deck = deckRepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));

        if(bindingResult.hasErrors()){
            model.addAttribute("deck", deck);
            return "addword";
        }
        card.setDeck(deck);
        cardRepository.save(card);
        return "redirect:/main";
    }
    @GetMapping("/deletecard")
    public String deleteCard(@RequestParam Long deckid, @RequestParam Long cardid){
        cardRepository.deleteById(cardid);
        return "redirect:/edit/" + deckid;
    }

}
