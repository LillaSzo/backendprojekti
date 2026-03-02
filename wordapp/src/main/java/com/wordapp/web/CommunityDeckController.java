package com.wordapp.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.wordapp.domain.AppUser;
import com.wordapp.domain.AppUserRepository;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;
import com.wordapp.domain.LanguageRepository;

@Controller
public class CommunityDeckController {

    private final DeckRepository deckRepository;
    private final AppUserRepository appUserRepository;

    public CommunityDeckController(DeckRepository deckRepository, AppUserRepository appUserRepository){
        this.deckRepository = deckRepository;
        this.appUserRepository = appUserRepository;
    }

    //https://www.baeldung.com/get-user-in-spring-security
    private AppUser getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    return appUserRepository.findByUsername(username);
    }

    @GetMapping("/community")
    public String showCommunity(Model model){

        List<Deck> adminDecks = deckRepository.findByUserIdRole("ADMIN");     
        model.addAttribute("decks", adminDecks);
        return "community";
    }

    @PostMapping("/deck/{id}/join")
    public String joinDeck(@PathVariable ("id") Long deckid){

        Deck deck = deckRepository.findById(deckid)
                        .orElseThrow(()-> new IllegalArgumentException("Deck not found"));
        AppUser currentUser = getCurrentUser();
        currentUser.getJoinedDeck().add(deck);
        appUserRepository.save(currentUser);
        
        return "redirect:/community";
    }

}
