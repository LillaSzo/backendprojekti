package com.wordapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wordapp.domain.CardRepository;
import com.wordapp.domain.Card;
import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;

@Controller
public class PracticeController {

    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;

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

    @PostMapping("/practice/deck/{deckid}/card/{cardid}/check")
    public String checkWord(@PathVariable Long deckid, @PathVariable Long cardid, @RequestParam String answer, RedirectAttributes redirectAttributes) {
 
    Card card = cardRepository.findById(cardid)
                            .orElseThrow(()-> new IllegalArgumentException("Card not found"));;

    boolean correct = answer.trim().equalsIgnoreCase(card.getTargetWord().trim());

    redirectAttributes.addFlashAttribute("cardid", cardid);
    redirectAttributes.addFlashAttribute("correct", correct);
    redirectAttributes.addFlashAttribute("message", correct ? "Correct!" : "Correct answer is: " + card.getTargetWord());
    
    return "redirect:/practice/deck/" + deckid + "?practice=true";
    }

}
