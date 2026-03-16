package com.wordapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
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
    @WithMockUser(authorities={"ADMIN"})
    public void testPostCard() throws Exception {

        String newCardJson = """
        {
        "targetWord": "postkortti",
        "translation": "test",
        "sentence": "Post testi"
        }
        """;
        
    mockMvc.perform(post("/decks/1/card")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newCardJson))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testPutCard() throws Exception {

    List <Card> cards = cardRepository.findByTargetWord("postkortti");
    Card card = cards.get(0);

        String putCardJson = """
        {
        "cardid": 2,
        "targetWord": "päivitys",
        "translation": "update",
        "sentence": "Put testi",
        "deck": {
        "deckid": 
        }
        }
        """;
    mockMvc.perform(put("/cards/" + card.getCardid())
        .contentType(MediaType.APPLICATION_JSON)
        .content(putCardJson))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testDeleteCard() throws Exception {

    List<Card> cards = cardRepository.findByTargetWord("päivitys");
    Card card = cards.get(0);

    mockMvc.perform(delete("/cards/" + card.getCardid()))
        .andExpect(status().isOk());

    List<Card> newCards = cardRepository.findByTargetWord("poistaa");
    assertThat(newCards).hasSize(0);
    }


}
