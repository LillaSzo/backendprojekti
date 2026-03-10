package com.wordapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import com.wordapp.web.AppUserController;
import com.wordapp.web.CardController;
import com.wordapp.web.CommunityDeckController;
import com.wordapp.web.DeckController;
import com.wordapp.web.PracticeController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class WordappApplicationTests {

	@Autowired
	private AppUserController appUserController;

	@Autowired
	private CardController cardController;

	@Autowired
	private CommunityDeckController communityDeckController;

	@Autowired
	private DeckController deckController;

	@Autowired
	private PracticeController practiceController;

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void appUserControllerLoads() throws Exception {
		assertThat(appUserController).isNotNull();
	}

	@Test
	public void cardControllerLoads() throws Exception {
		assertThat(cardController).isNotNull();
	}

	@Test
	public void communityDeckControllerLoads() throws Exception {
		assertThat(communityDeckController).isNotNull();
	}

	@Test
	public void deckControllerLoads() throws Exception {
		assertThat(deckController).isNotNull();
	}

	@Test
	public void practiceControllerLoads() throws Exception {
		assertThat(practiceController).isNotNull();
	}

	@Test
	public void testGetLoginPage() throws Exception {
		this.mockMvc.perform(get("/login"))
					.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin")
	public void testGetMain() throws Exception {
		this.mockMvc.perform(get("/main"))
					.andExpect(status().isOk());
	}

	@Test
	public void testGetCreate() throws Exception {
		this.mockMvc.perform(get("/create"))
					.andExpect(status().isOk());
	}

	@Test
	public void testGetCommunity() throws Exception {
		this.mockMvc.perform(get("/community"))
					.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin")
	public void testGetEdit() throws Exception {
		this.mockMvc.perform(get("/edit/2"))
					.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin")
	public void testGetAddWord() throws Exception {
		this.mockMvc.perform(get("/deck/2/addword"))
					.andExpect(status().isOk());
	}

	@Test
	public void testGetSignup() throws Exception {
		this.mockMvc.perform(get("/signup"))
					.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "user")
	public void testGetPractice() throws Exception {
		this.mockMvc.perform(get("/practice/deck/1"))
					.andExpect(status().isOk());
	}
}
