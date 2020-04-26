package com.neurosevent.reader.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.neurosevent.reader.domain.Message;

@Repository
public interface MessageRepo extends MongoRepository<Message, String> {
	
	List<Message> findByLastSentDateGreaterThan(Instant instant);
	
	List<Message> findByLastSentDateBetween(Instant startInstant,Instant endInstant);
	

}
