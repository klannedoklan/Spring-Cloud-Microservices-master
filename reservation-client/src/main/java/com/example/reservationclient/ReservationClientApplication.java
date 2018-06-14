package com.example.reservationclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.reservationclient.service.ReservationClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ReservationClientApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ReservationClientApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	return objectMapper;
    }
    
    @Bean
    public ReservationClientService reservationClientService() {
    	return new ReservationClientService();
    }
    
    

}