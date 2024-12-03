package com.example.Bucket4jTest.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Period;

@RestController
@RequestMapping("/token")
public class RequestController {
    private Bucket bucket;
    @GetMapping("/produce")
    public ResponseEntity<String> generateToken(){
        Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(10, refill);
        bucket = Bucket.builder()
                .addLimit(limit)
                .build();
        return new ResponseEntity<String>("Genearated Successfully!", HttpStatus.OK);
    }

    @GetMapping("/consume")
    public ResponseEntity<String> consumeToken(){
        if(bucket.tryConsume(1)){
            return new ResponseEntity<>("Genrated token", HttpStatus.OK);
        }
        return new ResponseEntity<>("tokens empty!", HttpStatus.TOO_MANY_REQUESTS);
    }
}
