package com.wordapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.wordapp.domain.Card;
import com.wordapp.domain.CardRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RestCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository cardRepository;
    @Test
    public void testGetAllCards() throws Exception {

        mockMvc.perform(get("/cards"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetWithId() throws Exception {

        mockMvc.perform(get("/cards/1"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    public void testPostCard() throws Exception {

        String newCardJson = """
        {
        "targetWord": "testi",
        "translation": "test",
        "sentence": "Post test"
        }
        """;
        
    mockMvc.perform(post("/decks/2/card")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newCardJson))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    public void testPutCard() throws Exception {

        String putCardJson = """
        {
        "cardid": 3,
        "targetWord": "päivitys",
        "translation": "update",
        "sentence": "Put testi",
        "deck": {
        "deckid": 2
        }
        }
        """;
    mockMvc.perform(put("/cards/3")
        .contentType(MediaType.APPLICATION_JSON)
        .content(putCardJson))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    public void testDeleteCard() throws Exception {

    mockMvc.perform(delete("/cards/3"))
        .andExpect(status().isOk());

    List<Card> cards = cardRepository.findByTargetWord("päivitys");
        Card card = cards.get(0);
        cardRepository.delete(card);
        List<Card> newCards = cardRepository.findByTargetWord("päivitys");
        assertThat(newCards).hasSize(0);
    }


}
