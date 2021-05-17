package com.example.service;

import com.example.entity.Card;
import com.example.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public Mono<Void> insert(Mono<Card> cardMono) {
        return cardMono
                .flatMap(repository::save)
                .then();
    }
}
