package com.Employee.EmployeeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan("com.Employee")
@EnableEurekaClient
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

}
