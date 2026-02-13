package com.apiGateway.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Fallbackcontroller {

    @RequestMapping("/employeeServiceFallback")
    public Mono<String> employeeServiceFallback(){
        return Mono.just("Employee service is down, please try again later");
    }

    @RequestMapping("/addressServiceFallback")
    public Mono<String> addressServiceFallback(){
        return Mono.just("Address Service is not working, please try again later");
    }
}
