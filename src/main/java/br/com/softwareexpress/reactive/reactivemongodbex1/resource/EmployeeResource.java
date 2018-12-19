package br.com.softwareexpress.reactive.reactivemongodbex1.resource;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.softwareexpress.reactive.reactivemongodbex1.model.Employee;
import br.com.softwareexpress.reactive.reactivemongodbex1.model.EmployeeEvent;
import br.com.softwareexpress.reactive.reactivemongodbex1.repository.EmployeeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeResource {

	@Autowired
	private EmployeeRepository repository;
	
	@GetMapping
	public Flux<Employee> getAll() {
		return repository
			.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Employee> getId(@PathVariable("id") final String empId) {
		return repository.findById(empId);
	}

	@GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Employee> getEvents(@PathVariable("id") final String empId) {
		return repository
				.findById(empId)
				.flatMapMany(employee -> {
					Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
					Flux<EmployeeEvent> employeeEventFlux = 
						Flux.fromStream(
							Stream.generate(() -> new EmployeeEvent(employee, new Date()))
						);
					
					return Flux.zip(interval, employeeEventFlux).map(e -> e.getT2().getEmployee());
					
				});
	}
	
}
