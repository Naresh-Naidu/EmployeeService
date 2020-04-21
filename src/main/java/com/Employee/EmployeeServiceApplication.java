package com.Employee;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.Employee.Model.Employee;
import com.Employee.Repository.EmployeeRepository;
import com.microsoft.azure.spring.data.cosmosdb.repository.config.EnableDocumentDbRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableDocumentDbRepositories(basePackages = "com.Employee.Repository")
public class EmployeeServiceApplication {

	@LoadBalanced
	@Bean
	public RestTemplate getTemplate() {
		RestTemplate rt=new RestTemplate();
		rt.setRequestFactory( new HttpComponentsClientHttpRequestFactory() );
		return rt;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}
	
	@Autowired
	private EmployeeRepository repository;

	
	public void run(String... args) throws Exception {
        // Create a unique identifier.
        String uuid = UUID.randomUUID().toString();

        // Create a new User class.
        final Employee testEmployee = new Employee(uuid, "John", 11,21);

        // For this example, remove all of the existing records.
        //repository.deleteAll();

        // Save the User class to the Azure database.
        repository.save(testEmployee);

        // Retrieve the database record for the User class you just saved by ID.
       // Optional<Employee> result = repository.findById(testEmployee.getId());

        // Display the results of the database record retrieval.
       // System.out.println("\nSaved user is: " + result + "\n");
    }
}
