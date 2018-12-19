package br.com.softwareexpress.reactive.reactivemongodbex1;

import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.softwareexpress.reactive.reactivemongodbex1.model.Employee;
import br.com.softwareexpress.reactive.reactivemongodbex1.repository.EmployeeRepository;

@SpringBootApplication
public class ReactiveMongodbEx1Application {

	@Bean
	CommandLineRunner employees(EmployeeRepository repo) {
		return args -> {
			repo
				.deleteAll()
				.subscribe(null, null, () -> {
					Stream.of(
						new Employee(UUID.randomUUID().toString(), "Martins", 10400L),
						new Employee(UUID.randomUUID().toString(), "Miragaia", 12700L),
						new Employee(UUID.randomUUID().toString(), "Drausio", 6200L),
						new Employee(UUID.randomUUID().toString(), "Camargo", 7500L),
						new Employee(UUID.randomUUID().toString(), "Juvenal", 8900L)
					).forEach(e -> {
						repo
							.save(e)
							.subscribe(System.out::println);
						});
					});
					
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReactiveMongodbEx1Application.class, args);
	}
}
