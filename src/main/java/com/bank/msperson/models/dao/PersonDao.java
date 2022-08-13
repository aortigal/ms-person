package com.bank.msperson.models.dao;

import com.bank.msperson.models.documents.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonDao extends ReactiveMongoRepository<Person, String> {

}
