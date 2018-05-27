package com.customerradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.customerradar.*"} , 
							exclude = JpaRepositoriesAutoConfiguration.class)
@EnableJpaRepositories(basePackages="com.customerradar.repository")
public class CustomerServiceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceAppApplication.class, args);
	}
}
