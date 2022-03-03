package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    //publisher
    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("alex", "ben", "Chloe"))
                .log(); //could be coming from db or a remote service call
    }

    public Flux<String> namesFluxMap(int stringLength){
        //filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s-> s.length() > stringLength)
                .map(s->s.length() + "-" + s) //this one gives you the number lenght of alex or chloe
                .log();
    }

    public Flux<String> namesFlux_immutability(){
        Flux<String> namesFlux = Flux.fromIterable(List.of("alex", "ben", "Chloe"));
        namesFlux.map(String::toUpperCase); //wont actually perform the upper case operation
        //so when we assign the flux to a variable, the operation is performed on the variable meaning the test has to be the original input
        return namesFlux;

    }

    public Mono<List<String>> namesMono_flatmap(int stringLength){
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s->s.length() > stringLength)
                .flatMap(this::splitStringMono); //want Mono<List of A, L, E, X>
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray= s.split("");
        var charlist = List.of(charArray); //ALEX -> A, L, E, X
        return Mono.just(charlist);
    }

    public Flux<String> namesFlux_flatMap(int stringLength){
        //filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s-> s.length() > stringLength)
                //A, L, E, X, C, H, L, O, E
                .flatMap(s-> splitString(s))
                .log();
    }

    public Flux<String> namesFlux_flatMap_ASYNC(int stringLength){
        //filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s-> s.length() > stringLength)
                //A, L, E, X, C, H, L, O, E
                .flatMap(s-> splitString_withDelay(s))
                .log();
    }

    public Flux<String> namesFlux_concatmap(int stringLength){
        //filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s-> s.length() > stringLength)
                //A, L, E, X, C, H, L, O, E
                .concatMap(s-> splitString_withDelay(s))
                .log();
    }

    //ALEX -> Flux(A,L,E,X)
    public Flux<String> splitString(String name){
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitString_withDelay(String name){
        var charArray = name.split("");
//        var delay=  new Random().nextInt(1000);
        var delay=  1000;
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Mono<String> namedMono(){
        return Mono.just("alex").log();
    }

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        //in order to get the values from namesFlux we have to subscribe to it
        fluxAndMonoGeneratorService.namesFlux().subscribe(name -> {
            System.out.println("name is : " + name);
        });

        fluxAndMonoGeneratorService.namedMono().subscribe(name -> {
            System.out.println("Mono name is : " + name);
        });

    }
}
