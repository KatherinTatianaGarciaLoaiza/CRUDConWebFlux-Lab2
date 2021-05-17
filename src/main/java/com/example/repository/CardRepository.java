package com.example.repository;

import com.example.entity.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CardRepository extends ReactiveMongoRepository<Card, String> {
    //Mono<Card> findByNumber(String number);
}
