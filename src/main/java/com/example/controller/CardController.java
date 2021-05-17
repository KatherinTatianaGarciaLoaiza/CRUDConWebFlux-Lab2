package com.example.controller;


import com.example.entity.Card;
import com.example.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping(value = "/crear-tarjeta")
    public Mono<Void> post(@RequestBody Mono<Card> cardMono) {
        return cardService.insert(cardMono);
    }
    //Create, Read, Update, Delete, List

}
