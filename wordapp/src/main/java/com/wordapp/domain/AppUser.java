package com.wordapp.domain;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name="app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
	@JsonIgnore
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

	//https://www.baeldung.com/jpa-many-to-many
	@ManyToMany
	@JoinTable(
		name = "joined_decks",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "deck_id"))
	@JsonIgnoreProperties("joinedUsers")
	Set<Deck> joinedDeck;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
	@JsonIgnoreProperties("userId")
    private List<Deck> decklist;

    
    public AppUser() {
    }

	public AppUser(String username, String passwordHash, String role) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Deck> getDecklist() {
		return decklist;
	}

	public void setDecklist(List<Deck> decklist) {
		this.decklist = decklist;
	}

	public Set<Deck> getJoinedDeck() {
		return joinedDeck;
	}

	public void setJoinedDeck(Set<Deck> joinedDeck) {
		this.joinedDeck = joinedDeck;
	}

	
	
}
