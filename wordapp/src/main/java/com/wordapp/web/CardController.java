package com.wordapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.wordapp.domain.Card;
import com.wordapp.domain.CardRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;
import com.wordapp.domain.LanguageRepository;

import jakarta.validation.Valid;

@Controller
public class CardController {

    private CardRepository crepository;
    private DeckRepository drepository;
    private LanguageRepository lrepository;

    public CardController(CardRepository crepository, DeckRepository drepository, LanguageRepository lrepository){
        this.crepository = crepository;
        this.drepository = drepository;
        this.lrepository = lrepository;
    }

    @GetMapping("/main")
    public String showMain(Model model){
        model.addAttribute("decks", drepository.findAll());
        return "main";
    }

    @GetMapping("/create")
    public String createDeck(Model model){
        model.addAttribute("deck", new Deck());
        model.addAttribute("languages", lrepository.findAll());
        return "createdeck";
    }
    @PostMapping("/create")
    public String saveDeck(@Valid Deck deck, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("languages", lrepository.findAll());
            return"createdeck";
        }
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
        model.addAttribute("languages", lrepository.findAll());
        return "editdeck";
    }

    @PostMapping("/edit/{id}")
    public String editDeck(@PathVariable("id") Long deckid, @Valid Deck deck, BindingResult bindingresult, Model model){
        
        if(bindingresult.hasErrors()){
            model.addAttribute("languages", lrepository.findAll());
            return "editdeck";
        }
        drepository.save(deck);
        return "redirect:/main";
    }

    @GetMapping("/deck/{id}/addword")
    public String createCard(@PathVariable("id") Long deckid, Model model, Card card){
        Deck deck = drepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));

        card.setDeck(deck);

        model.addAttribute("deck", deck);
        model.addAttribute("card", card);
        return "addword";
    }

    @PostMapping("/deck/{id}/addword")
    public String saveWords(@PathVariable("id") Long deckid, @Valid Card card, BindingResult bindingResult, Model model){

        Deck deck = drepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));

        if(bindingResult.hasErrors()){
            model.addAttribute("deck", deck);
            model.addAttribute("card", card);
            return "addword";
        }
        card.setDeck(deck);
        crepository.save(card);
        return "redirect:/main";
    }

}
