package com.bank.msperson.services.impl;

import com.bank.msperson.handler.ResponseHandler;
import com.bank.msperson.models.dao.PersonDao;
import com.bank.msperson.models.documents.Person;
import com.bank.msperson.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao dao;

    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Override
    public Mono<ResponseHandler> create(Person p) {
        p.setCreatedDate(LocalDateTime.now());
        return dao.save(p)
                .doOnNext(person -> log.info(person.toString()))
                .map(person -> new ResponseHandler("Done", HttpStatus.OK, person))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> findAll() {

        return dao.findAll().map(person -> {
                    person.setFirstName(person.getFirstName().toUpperCase());
                    person.setLastName(person.getLastName().toUpperCase());
                    return person;
                })
                .doOnNext(person -> log.info(person.toString()))
                .collectList().map(persons -> new ResponseHandler("Done", HttpStatus.OK, persons))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));

    }

    @Override
    public Mono<ResponseHandler> find(String id) {
        return dao.findById(id)
                .doOnNext(person -> log.info(person.toString()))
                .map(person -> new ResponseHandler("Done", HttpStatus.OK, person))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> update(String id, Person p) {
        return dao.existsById(id).flatMap(check -> {
            if (check){
                p.setUpdateDate(LocalDateTime.now());
                return dao.save(p)
                        .doOnNext(person -> log.info(person.toString()))
                        .map(person -> new ResponseHandler("Done", HttpStatus.OK, person))
                        .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
            }
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));

        });
    }

    @Override
    public Mono<ResponseHandler> delete(String id) {

        return dao.existsById(id).flatMap(check -> {
            if (check)
                return dao.deleteById(id).then(Mono.just(new ResponseHandler("Done", HttpStatus.OK, null)));
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));
        });
    }
}
