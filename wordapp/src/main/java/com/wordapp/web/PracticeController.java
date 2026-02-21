package com.wordapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wordapp.domain.CardRepository;
import com.wordapp.domain.Card;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;

@Controller
public class PracticeController {

    private DeckRepository deckRepository;
    private CardRepository cardRepository;

    public PracticeController (DeckRepository deckRepository, CardRepository cardRepository){
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
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

    @PostMapping("/check")
    public String checkWord(@RequestParam Long cardid, @RequestParam Long deckid, @RequestParam String answer, Model model) {
 
    Deck deck = deckRepository.findById(deckid)
                            .orElseThrow(() -> new IllegalArgumentException("Deck not found"));

    Card card = cardRepository.findById(cardid)
                            .orElseThrow(()-> new IllegalArgumentException("Card not found"));;

    boolean correct = answer.trim().equalsIgnoreCase(card.getTranslation().trim());

    model.addAttribute("deck", deck);
    model.addAttribute("cards",cardRepository.findByDeckDeckid(deckid));
    model.addAttribute("practice", true);
    model.addAttribute("cardid", cardid);
    model.addAttribute("correct", correct);
    model.addAttribute("message", correct ? "Correct!" : ("Correct answer is: " + card.getTranslation()));

    return "practice";
}

}
