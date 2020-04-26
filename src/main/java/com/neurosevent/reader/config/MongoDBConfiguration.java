package com.neurosevent.reader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.neurosevent.reader.repository")
public class MongoDBConfiguration {

}
