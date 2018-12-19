package br.com.softwareexpress.reactive.reactivemongodbex1.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.com.softwareexpress.reactive.reactivemongodbex1.model.Employee;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String>{

}
