package com.bank.msperson.services;

import com.bank.msperson.handler.ResponseHandler;
import com.bank.msperson.models.documents.Person;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<ResponseHandler> create(Person p);

    Mono<ResponseHandler> findAll();

    Mono<ResponseHandler> find(String id);

    Mono<ResponseHandler> update(String id, Person p);

    Mono<ResponseHandler> delete(String id);
}
