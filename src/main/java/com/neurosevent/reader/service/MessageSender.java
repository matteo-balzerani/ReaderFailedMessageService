package com.neurosevent.reader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neurosevent.reader.domain.Message;

@Service
public class MessageSender {

	private final Logger log = LoggerFactory.getLogger(MessageSender.class);

	@Autowired
	private HttpHeaders httpHeaders;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objMapper;
	
	@Value("${mocked_sub.url}")
	private String subUrl;

	public Boolean sendToSubscriber(Message data) {
		try {
			String jsonStr = objMapper.writeValueAsString(data);
			HttpEntity<String> request = new HttpEntity<String>(jsonStr, httpHeaders);
			ResponseEntity<String> resultPost = restTemplate.postForEntity(subUrl, request,
					String.class);
			log.info(resultPost.toString());
		} catch (Exception e) {
			log.error("error:  " + e.getMessage());
			return false;
		}
		return true;
	}

}
