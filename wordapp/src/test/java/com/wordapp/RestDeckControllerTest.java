package com.wordapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RestDeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
            "userId": {
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
    @WithMockUser(username = "admin")
    public void testPutDeck () throws Exception {
        String updatedDeckJson = """
            {
            "name": "Update Test Deck",
            "targetLanguage": {
            "name": "Finnish",
            "id": 1
            },
            "translationLanguage": {
            "name": "English",
            "id": 2
            },
            "userId": {
            "username": "admin",
            "role": "ADMIN",
            "id": 2
            },
            "wordcount": 0,
            "cardlist": [],
            "deckid": 10
            }
            """;
        mockMvc.perform(put("/decks/10")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedDeckJson))
        .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin")
    public void testDeleteDeck () throws Exception {

        mockMvc.perform(delete("/decks/10"))
            .andExpect(status().isOk());
    }
}    
