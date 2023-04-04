package msa.rider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@SpringBootApplication
@EnableMongoRepositories
@EnableAutoConfiguration
public class RiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiderApplication.class, args);
	}

}
