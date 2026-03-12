package com.wordapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.wordapp.domain.Deck;
import com.wordapp.domain.DeckRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RestDeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeckRepository deckRepository;

    @Test
    public void testGetAllDecks() throws Exception {
        mockMvc.perform(get("/decks"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetDeckWithId() throws Exception {
        mockMvc.perform(get("/decks/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testPostDeck() throws Exception {
       String newDeckJson = """
            {
            "name": "Testin Test Deck",
            "targetLanguage": {
            "name": "Finnish",
            "id": 1
            },
            "translationLanguage": {
            "name": "English",
            "id": 2
            },
            "user": {
            "username": "admin",
            "role": "ADMIN",
            "id": 2
            },
            "wordcount": 0,
            "cardlist": []
            }
            """;

    mockMvc.perform(post("/deck")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newDeckJson))
        .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testPutDeck () throws Exception {

    List<Deck> decks = deckRepository.findByName("REST Test Deck");
    Deck deck = decks.get(0);

        String updatedDeckJson = """
            {
            "name": "Updated REST Test Deck",
            "targetLanguage": {
            "name": "Finnish",
            "id": 1
            },
            "translationLanguage": {
            "name": "English",
            "id": 2
            },
            "user": {
            "username": "admin",
            "role": "ADMIN",
            "id": 2
            },
            "wordcount": 0,
            "cardlist": [],
            "deckid": 3
            }
            """;
        mockMvc.perform(put("/decks/" + deck.getDeckid())
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedDeckJson))
        .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testDeleteDeck () throws Exception {
    
    List<Deck> decks = deckRepository.findByName("Testin Test Deck");
    Deck deck = decks.get(0);

        mockMvc.perform(delete("/decks/" + deck.getDeckid()))
            .andExpect(status().isOk());
    
    List<Deck> newDecks = deckRepository.findByName("Testin Test Deck");
    assertThat(newDecks).hasSize(0);
    }

}    
