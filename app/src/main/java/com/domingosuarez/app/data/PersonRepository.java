package com.domingosuarez.app.data;

import org.springframework.data.repository.ListCrudRepository;

public interface PersonRepository extends ListCrudRepository<Person, Long> {
}
