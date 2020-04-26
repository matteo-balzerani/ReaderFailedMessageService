package com.neurosevent.reader.service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.neurosevent.reader.domain.Message;
import com.neurosevent.reader.repository.MessageRepo;

@Service
public class MessageConsumer {

	private final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private MessageRepo messageRepo;

	private final int RATE_5_MINS = 10000;
	private final int RATE_10_MINS = 30000;

	@Scheduled(fixedRate = RATE_5_MINS)
	public void read5mins() {
		try {
			List<Message> messages = messageRepo
					.findByLastSentDateGreaterThan(Instant.now().minusSeconds(TimeUnit.MINUTES.toSeconds(5)));
			log.info("5mis check- {}", messages.size());
			messages.parallelStream().forEach(mess -> {
				if (messageSender.sendToSubscriber(mess)) {
					messageRepo.delete(mess);
				}
			});
		} catch (Exception ex) {
			log.error("error:::{}", ex.toString());
		}

	}

	@Scheduled(fixedRate = RATE_10_MINS)
	public void read10mins() {
		try {
			List<Message> messages = messageRepo.findByLastSentDateBetween(
					Instant.now().minusSeconds(TimeUnit.MINUTES.toSeconds(5)),
					Instant.now().minusSeconds(TimeUnit.MINUTES.toSeconds(10)));
			log.info("10mins check- {}", messages.size());
			messages.parallelStream().forEach(mess -> {
				if (messageSender.sendToSubscriber(mess)) {
					messageRepo.delete(mess);
				}
			});
		} catch (Exception ex) {
			log.error("error:::{}", ex.toString());
		}

	}
}
