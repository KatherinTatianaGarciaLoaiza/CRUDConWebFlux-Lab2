package com.example.service;

import com.example.entity.Card;
import com.example.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

import static com.example.validateCard.Validate.validateCard;

@Service
public class CardService {

    private final BiFunction<CardRepository, Card, Mono<Card>> validateBeforeInsert
            =(repo, card) -> repo.findByNumber(card.getNumber());

    @Autowired
    private CardRepository repository;

    public Mono<Card> insert(Mono<Card> cardMono) {
        return cardMono
                .flatMap(card ->{
                    card.setType(validateCard(card.getCode()));
                    return repository.save(card);
                });
    }

    public Flux<Card> listAll(){
        return repository.findAll();
    }
}
