package com.wordapp.domain;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id", nullable = false, updatable = false)
    private Long cardid;

    @Size(min=2, max=20)
    @Column(name="target_word")
    private String targetWord;

    @Size(min=2, max=20)
    @Column(name="translation")
    private String translation;
    
    @Column(name="sentence")
    private String sentence;

    @ManyToOne
    @JoinColumn(name="deck_id")
    @JsonIgnoreProperties({"cardlist", "name", "targetLanguage", "translationLanguage", "user"})
    private Deck deck;

    public Card(){}

    public Card(String targetWord, String translation, String sentence, Deck deck) {
        this.targetWord = targetWord;
        this.translation = translation;
        this.sentence = sentence;
        this.deck = deck;
    }

    public Long getCardid() {
        return cardid;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public String getTranslation() {
        return translation;
    }

    public String getSentence() {
        return sentence;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setCardid(Long cardid) {
        this.cardid = cardid;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return "Card [targetWord=" + targetWord + ", translation=" + translation + ", sentence=" + sentence + "]";
    }

    

}
