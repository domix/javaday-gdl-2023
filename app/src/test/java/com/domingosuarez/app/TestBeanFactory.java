package com.domingosuarez.app;

import com.domingosuarez.app.data.PersonRepository;
import com.domingosuarez.app.service.PersonService;
import com.domingosuarez.bv.vavr.ValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestBeanFactory {
    @Bean
    ValidationService validationService() {
        return new ValidationService();
    }

    @Bean
    PersonService personService(PersonRepository repository, ValidationService validationService) {
        return new PersonService(repository, validationService);
    }
}
