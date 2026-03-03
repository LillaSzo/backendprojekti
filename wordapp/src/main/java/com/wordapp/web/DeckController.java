package com.wordapp.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.wordapp.domain.AppUser;
import com.wordapp.domain.AppUserRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;
import com.wordapp.domain.LanguageRepository;

import jakarta.validation.Valid;

@Controller
public class DeckController {

    private final DeckRepository deckRepository;
    private final LanguageRepository languageRepository;
    private final AppUserRepository appUserRepository;

    public DeckController(DeckRepository deckRepository, LanguageRepository languageRepository, AppUserRepository appUserRepository){
        this.deckRepository = deckRepository;
        this.languageRepository = languageRepository;
        this.appUserRepository = appUserRepository;
    }
    
    //https://www.baeldung.com/get-user-in-spring-security
    private AppUser getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    return appUserRepository.findByUsername(username);
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/main")
    public String showDecks(Model model){
        AppUser currentUser = getCurrentUser();
        
        model.addAttribute("decks", currentUser.getDecklist());
        model.addAttribute("joined", currentUser.getJoinedDeck());

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
        deck.setUserId(getCurrentUser());
        deckRepository.save(deck);
        return "redirect:/main";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @deckRepository.findById(#deckid).get().userId.username == authentication.name")
    public String deleteDeck(@PathVariable("id") Long deckid){
        deckRepository.deleteById(deckid);
        return "redirect:/main";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @deckRepository.findById(#deckid).get().userId.username == authentication.name")
    public String pickEdit(@PathVariable("id") Long deckid, Model model){
        Deck deck = deckRepository.findById(deckid)
                                .orElseThrow(()-> new IllegalArgumentException("Deck not found"));
        model.addAttribute("deck", deck);
        model.addAttribute("cards", deck.getCardlist());
        model.addAttribute("languages", languageRepository.findAll());
        return "editdeck";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @deckRepository.findById(#deckid).get().userId.username == authentication.name")
    public String editDeck(@PathVariable("id") Long deckid, @Valid Deck deck, BindingResult bindingresult, Model model){
        
        if(bindingresult.hasErrors()){
            model.addAttribute("languages", languageRepository.findAll());
            return "editdeck";
        }
        deck.setUserId(getCurrentUser());
        deckRepository.save(deck);
        return "redirect:/main";
    }

}
