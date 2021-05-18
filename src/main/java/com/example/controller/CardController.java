package com.example.controller;


import com.example.entity.Card;
import com.example.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping(value = "/createcard")
    public Mono<Card> createCard(@RequestBody Mono<Card> cardMono) {
        return cardService.insert(cardMono);
    }

    @GetMapping(value =  "/cardlist")
    public Flux<Card> cardList(){
        return cardService.listAll();
    }

    @GetMapping(value = "/ById/{id}")
    public  Mono<Card> getCardById(@PathVariable("id") String id){
        return cardService.getCardById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return cardService.deleteCard(id);
    }

    //Read, Update, Delete, List

}
