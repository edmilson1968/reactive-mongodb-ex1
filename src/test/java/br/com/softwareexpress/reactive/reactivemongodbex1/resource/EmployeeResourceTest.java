package br.com.softwareexpress.reactive.reactivemongodbex1.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.softwareexpress.reactive.reactivemongodbex1.model.Employee;
import br.com.softwareexpress.reactive.reactivemongodbex1.repository.EmployeeRepository;
import reactor.test.StepVerifier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:reactive-infrastructure.xml")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class EmployeeResourceTest {

	@Autowired ReactiveMongoTemplate template;
	ReactiveMongoRepositoryFactory factory;
	ClassLoader classLoader;
	BeanFactory beanFactory;
	EmployeeRepository repository;

	Employee joao, matheus, paulo, rico, antonio, renato, maria;
	
	@Autowired 
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {

		factory = new ReactiveMongoRepositoryFactory(template);
		factory.setRepositoryBaseClass(SimpleReactiveMongoRepository.class);
		factory.setBeanClassLoader(classLoader);
		factory.setBeanFactory(beanFactory);
		//factory.setEvaluationContextProvider(QueryMethodEvaluationContextProvider.DEFAULT);

		repository = factory.getRepository(EmployeeRepository.class);

		StepVerifier.create(repository.deleteAll()).verifyComplete();
		
		joao = new Employee(UUID.randomUUID().toString(), "Joao", 21030L);
		matheus = new Employee(UUID.randomUUID().toString(), "Matheus", 27200L);
		paulo = new Employee(UUID.randomUUID().toString(), "Paulo", 19380L);
		rico = new Employee(UUID.randomUUID().toString(), "Rico", 12060L);
		antonio = new Employee(UUID.randomUUID().toString(), "Antonio", 22300L);
		renato = new Employee(UUID.randomUUID().toString(), "Renato", 23800L);
		maria = new Employee(UUID.randomUUID().toString(), "Maria", 22750L);

		StepVerifier.create(repository.saveAll(
				Arrays.asList(matheus, joao, paulo, rico, antonio, renato, maria))) //
					.expectNextCount(7) //
					.verifyComplete();
	}

	@Test
	public void test() throws Exception {
		mockMvc.perform(get("/rest/employee"))
		.andExpect(status().isOk())
		;							
	}

}
