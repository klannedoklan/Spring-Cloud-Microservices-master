package com.example.reservationclient.service;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationClientService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public <Q> ResponseEntity<String> exchange(final String url, final HttpMethod httpMethod,
			final Map<String, String> headerValues, final Q body) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAll(headerValues);
		final HttpEntity<Q> request = new HttpEntity<>(body, headers);
		return this.restTemplate.exchange(url, httpMethod, request, String.class);
	}

}
