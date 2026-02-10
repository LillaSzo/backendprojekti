package com.wordapp.domain;

import jakarta.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardid;
    private String targetWord;
    private String translation;
    private String sentence;

    @ManyToOne
    @JoinColumn(name="deckid")
    private Deck deck;

    public Card(){}

    public Card(Long cardid, String targetWord, String translation, String sentence, Deck deck) {
        this.cardid = cardid;
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
