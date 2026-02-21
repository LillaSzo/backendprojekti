package com.wordapp.domain;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long>{
    List<Card> findByDeckDeckid(Long deckid);
}
