package org.cloudcafe.boot2.ms.social.app.chapter4.repository;

import org.cloudcafe.boot2.ms.social.app.chapter4.model.Employee;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String>, ReactiveQueryByExampleExecutor<Employee> {
}
