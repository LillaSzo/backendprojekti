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
public class RestCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

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
        
    mockMvc.perform(post("/decks/2/card")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newCardJson))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testPutCard() throws Exception {

    List <Card> cards = cardRepository.findByTargetWord("resttesti");
    Card card = cards.get(0);

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
    mockMvc.perform(put("/cards/" + card.getCardid())
        .contentType(MediaType.APPLICATION_JSON)
        .content(putCardJson))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities={"ADMIN"})
    public void testDeleteCard() throws Exception {

    Deck deck = deckRepository.findById(2L).orElseThrow();
    Card card = new Card();

    card.setTargetWord("poistaa");
    card.setTranslation("to delete");
    card.setSentence("Poistotestaus");
    card.setDeck(deck);
    cardRepository.save(card);

    mockMvc.perform(delete("/cards/" + card.getCardid()))
        .andExpect(status().isOk());

    List<Card> cards = cardRepository.findByTargetWord("poistaa");
    assertThat(cards).hasSize(0);
    }


}
