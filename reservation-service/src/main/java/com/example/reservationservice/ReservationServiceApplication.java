package com.example.reservationservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

import com.example.reservationservice.entity.Reservation;
import com.example.reservationservice.repository.ReservationRepository;

import java.util.Arrays;


@EnableDiscoveryClient
@SpringBootApplication
public class ReservationServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(final ReservationRepository reservationRepository) {
        return args -> {
            Arrays.asList("Ivan Trendafilov, Sidney El Agib, Sunny Verma, All of shared services".split(","))
                    .forEach(x -> reservationRepository.save(new Reservation(x)));
            reservationRepository.findAll().forEach(System.out::println);
        };
    }
    
    @Bean
    DispatcherServlet dispatcherServlet () {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }
}

