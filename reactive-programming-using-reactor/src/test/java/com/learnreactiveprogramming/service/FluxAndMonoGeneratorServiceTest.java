package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;


class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(namesFlux)
//                .expectNext("alex", "ben", "Chloe")
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        //given
        int stringLength = 3;
        //when
        Flux<String> map = fluxAndMonoGeneratorService.namesFluxMap(stringLength);
        //then
        StepVerifier.create(map)
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
        Flux<String> map = fluxAndMonoGeneratorService.namesFlux_immutability();

        StepVerifier.create(map)
                .expectNext("alex", "ben", "Chloe")
                .verifyComplete();

    }

    @Test
    void namesFlux_flatMap() {
        //given
        int stringLength = 3;
        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFlux_flatMap(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatMap_ASYNC() {
        //given
        int stringLength = 3;
        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFlux_flatMap_ASYNC(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        //given
        int stringLength = 3;
        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
//                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesMono_flatmap() {
        //given
        int stringLength = 3;
        //when
        Mono<List<String>> namesFlux = fluxAndMonoGeneratorService.namesMono_flatmap(stringLength);
        //then
        StepVerifier.create(namesFlux)
                .expectNext(List.of("A","L","E","X"))
//                .expectNextCount(1)
                .verifyComplete();
    }
}