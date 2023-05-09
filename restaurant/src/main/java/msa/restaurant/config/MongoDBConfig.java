package msa.restaurant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "msa.restaurant.repository")
public class MongoDBConfig {
}
