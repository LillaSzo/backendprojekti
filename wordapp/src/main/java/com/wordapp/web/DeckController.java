package com.wordapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;
import com.wordapp.domain.LanguageRepository;

import jakarta.validation.Valid;

@Controller
public class DeckController {

    private DeckRepository deckRepository;
    private LanguageRepository languageRepository;

    public DeckController(DeckRepository drepository, LanguageRepository lrepository){
        this.deckRepository = drepository;
        this.languageRepository = lrepository;
    }

    @GetMapping("/main")
    public String showDecks(Model model){
        model.addAttribute("decks", deckRepository.findAll());
        return "main";
    }

    @GetMapping("/create")
    public String createDeck(Model model){
        model.addAttribute("deck", new Deck());
        model.addAttribute("languages", languageRepository.findAll());
        return "createdeck";
    }
    @PostMapping("/create")
    public String saveDeck(@Valid Deck deck, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("languages", languageRepository.findAll());
            return"createdeck";
        }
        deckRepository.save(deck);
        return "redirect:/main";
    }

    @GetMapping("/delete/{id}")
    public String deleteDeck(@PathVariable("id") Long deckid){
        deckRepository.deleteById(deckid);
        return "redirect:/main";
    }

    @GetMapping("/edit/{id}")
    public String pickEdit(@PathVariable("id") Long deckid, Model model){
        Deck deck = deckRepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));
        model.addAttribute("deck", deck);
        model.addAttribute("cards", deck.getCardlist());
        model.addAttribute("languages", languageRepository.findAll());
        return "editdeck";
    }

    @PostMapping("/edit/{id}")
    public String editDeck(@PathVariable("id") Long deckid, @Valid Deck deck, BindingResult bindingresult, Model model){
        
        if(bindingresult.hasErrors()){
            model.addAttribute("languages", languageRepository.findAll());
            return "editdeck";
        }
        deckRepository.save(deck);
        return "redirect:/main";
    }

}
