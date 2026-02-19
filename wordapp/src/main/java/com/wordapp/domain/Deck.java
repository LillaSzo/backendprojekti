package com.wordapp.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deckid;
    @Size(min=2, max=30)
    private String name; 
    @ManyToOne
    @JoinColumn(name="targetlanguageid")
    private Language targetLanguage;  
    @ManyToOne
    @JoinColumn(name="translationlanguageid")
    private Language translationLanguage;
    private int wordcount;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck")
    private List<Card> cardlist;

    public Deck() {
    }

    public Deck(String name, Language targetLanguage, Language translationLanguage, int wordcount) {
        this.name = name;
        this.targetLanguage = targetLanguage;
        this.translationLanguage = translationLanguage;
        this.wordcount = wordcount;
    }

    public Long getDeckid() {
        return deckid;
    }

    public void setDeckid(Long deckid) {
        this.deckid = deckid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public Language getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(Language translationLanguage) {
        this.translationLanguage = translationLanguage;
    }

    public int getWordcount() {
        return cardlist.size();
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }

    public List<Card> getCardlist() {
        return cardlist;
    }

    public void setCardlist(List<Card> cardlist) {
        this.cardlist = cardlist;
    }

    @Override
    public String toString() {
        return "Deck [deckid=" + deckid + ", name=" + name + ", targetLanguage=" + targetLanguage
                + ", translationLanguage=" + translationLanguage + ", wordcount=" + wordcount + "]";
    }

    

}
