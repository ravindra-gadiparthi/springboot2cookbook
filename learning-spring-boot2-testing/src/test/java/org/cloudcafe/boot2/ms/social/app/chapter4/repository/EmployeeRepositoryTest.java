package org.cloudcafe.boot2.ms.social.app.chapter4.repository;


import org.cloudcafe.boot2.ms.social.app.chapter4.model.Employee;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoOperations;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@DataMongoTest
public class EmployeeRepositoryTest {


    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    EmployeeRepository employeeRepository;

    @Before
    public void init() {
        mongoOperations.dropCollection(Employee.class);

        Employee e1 = new Employee();
        e1.setId(UUID.randomUUID().toString());
        e1.setFirstName("Bilbo");
        e1.setLastName("Baggins");
        e1.setRole("burglar");
        mongoOperations.insert(e1);
        Employee e2 = new Employee();
        e2.setId(UUID.randomUUID().toString());
        e2.setFirstName("Frodo");
        e2.setLastName("Baggins");
        e2.setRole("ring bearer");
        mongoOperations.insert(e2);
    }

    @Test
    public void testFindAll() {
        StepVerifier.create(employeeRepository.findAll())
                // .expectNextCount(1)
                .expectComplete();
    }

    @Test
    public void testQueryByExample() {
        Employee employee = new Employee();
        employee.setFirstName("Bilbo");
        Example<Employee> example = Example.of(employee);
        StepVerifier.create(employeeRepository.findOne(example))
                .expectNextCount(1)
                .expectComplete();
    }

    @Test
    public void testExampleMatcher() {
        Employee employee = new Employee();
        employee.setFirstName("bilbo");

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withMatcher("firstName", startsWith())
                .withIgnoreNullValues();
        Example<Employee> example = Example.of(employee, matcher);
        StepVerifier.create(employeeRepository.findOne(example))
                .expectNextCount(1)
                .expectComplete();
    }


}
