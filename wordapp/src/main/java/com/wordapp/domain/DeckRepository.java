package com.wordapp.domain;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DeckRepository extends CrudRepository<Deck, Long>{

    List<Deck> findByUserIdRole(String role);
}
