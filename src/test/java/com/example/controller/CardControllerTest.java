package com.example.controller;

import com.example.entity.Card;
import com.example.repository.CardRepository;
import com.example.service.CardService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CardController.class)
class CardControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private CardService cardService;

    @Captor
    private ArgumentCaptor<Mono<Card>> argumentCaptor;

    @MockBean
    private CardRepository repository;

    @ParameterizedTest
    @CsvSource({"063458, Tatiana Loaiza"})
    void createCard(String number, String title){
        var request  = Mono.just(new Card("063458", "Tatiana Loaiza"));
        when(repository.save(any(Card.class))).thenReturn(request);
        webTestClient.post().uri("/card/createcard").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(request, Card.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult();
        verify(cardService).insert(argumentCaptor.capture());
        var card = argumentCaptor.getValue().block();
        assertEquals(number, card.getNumber());
        assertEquals(title, card.getTitle());
    }

    @Test
    void list() {
        var list = Flux.just(
                new Card("037856", "Tatiana Loaiza"),
                new Card("128524", "Teresa Loaiza")
        );
        when(repository.findAll()).thenReturn(list);
        webTestClient.get()
                .uri("/card/cardlist")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("Tatiana Loaiza")
                .jsonPath("$[0].number").isEqualTo("037856")
                .jsonPath("$[1].number").isEqualTo("128524")
                .jsonPath("$[1].title").isEqualTo("Teresa Loaiza");

        verify(cardService).listAll();
        verify(repository).findAll();
    }

    @Test
    void getCardById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(new Card("12857496", "Shiro Loaiza")));
        webTestClient.get()
                .uri("/card/ById/12857496")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Card.class)
                .consumeWith(cardEntityExchangeResult -> {
                    var card = cardEntityExchangeResult.getResponseBody();
                    assert card != null;
                });
    }

    @Test
    void delete() {
        webTestClient.delete()
                .uri("/card/delete/12-3456")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    void update() {
        var request = Mono.just(new Card("12635241","Andrea Loaiza"));
        when(repository.save(any(Card.class))).thenReturn(request);
        webTestClient.put()
                .uri("/card/cardupdate")
                .body(request, Card.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult();
    }

    @Test
    void listByType() {
        var list = Flux.just(
                new Card("037856", "Tatiana Loaiza"),
                new Card("128524", "Teresa Loaiza")
        );
        when(repository.findByType("MASTERCARD")).thenReturn(list);
        webTestClient.get()
                .uri("/card/getByType/MASTERCARD")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("Tatiana Loaiza")
                .jsonPath("$[0].number").isEqualTo("037856");

        verify(cardService).listCardByType("MASTERCARD");
        verify(repository).findByType("MASTERCARD");
    }

}