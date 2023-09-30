package com.domingosuarez.app.web;

import com.domingosuarez.app.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/people")
public class PersonController {
    private final PersonService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreatePersonCommand command) {
        return ResponseFactory.of(
            service.create(command)
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseFactory.of(
            service.findAll()
        );
    }
}
