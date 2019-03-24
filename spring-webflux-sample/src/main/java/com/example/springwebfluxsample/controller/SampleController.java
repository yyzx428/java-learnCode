package com.example.springwebfluxsample.controller;

import com.example.springwebfluxsample.dto.SampleQueryDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    @PostMapping("/getName")
    public Mono<String> getName(@RequestBody SampleQueryDTO queryDTO) {
        return Mono.just(queryDTO.getName());
    }
}
