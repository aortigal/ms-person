package com.bank.msperson.controllers;

import com.bank.msperson.handler.ResponseHandler;
import com.bank.msperson.models.documents.Person;
import com.bank.msperson.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/person")
public class PersonRestControllers {

    @Autowired
    private PersonService personService;

    @PostMapping
    public Mono<ResponseHandler> create(@Valid @RequestBody Person p) {
        return personService.create(p);
    }

    @GetMapping
    public Mono<ResponseHandler> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseHandler> find(@PathVariable String id) {
        return personService.find(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponseHandler> update(@PathVariable("id") String id,@Valid @RequestBody Person p) {
        return personService.update(id, p);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseHandler> delete(@PathVariable("id") String id) {
        return personService.delete(id);
    }
}
