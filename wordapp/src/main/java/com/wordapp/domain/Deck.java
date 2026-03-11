package com.wordapp.domain;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="decks")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id", nullable = false, updatable = false)
    private Long deckid;

    @Size(min=2, max=30)
    @Column(name = "name")
    private String name; 

    @ManyToOne
    @JoinColumn(name="target_language_id")
    private Language targetLanguage;

    @ManyToOne
    @JoinColumn(name="translation_language_id")
    private Language translationLanguage;

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck")
    @JsonIgnoreProperties({"deck", "targetWord", "translation", "sentence"})
    private List<Card> cardlist;
    
    @Transient
    @JsonIgnore
    private int wordcount;
    
    //https://www.baeldung.com/jpa-many-to-many
    @ManyToMany(mappedBy = "joinedDeck")
    @JsonIgnore
    Set <AppUser> joinedUsers;

    public Deck() {
    }

    public Deck(String name, Language targetLanguage, Language translationLanguage, AppUser userId) {
        this.name = name;
        this.targetLanguage = targetLanguage;
        this.translationLanguage = translationLanguage;
        this.user = userId;
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

    public AppUser getUser(){
        return user;
    } 
    public void  setUser(AppUser user){
        this.user = user;
    }

    public void setAppUserName(AppUser userId){
        this.user = userId;
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

    public Set<AppUser> getJoinedUsers() {
        return joinedUsers;
    }

    public void setJoinedUsers(Set<AppUser> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }
    
   
    
}
