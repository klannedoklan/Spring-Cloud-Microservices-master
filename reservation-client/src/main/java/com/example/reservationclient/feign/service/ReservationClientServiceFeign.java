package com.example.reservationclient.feign.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.reservationclient.entity.Reservation;

@FeignClient(name = "${reservationclient.root_uri}")
public interface ReservationClientServiceFeign {
	
	@PostMapping(path = "/add")
	void add(@RequestBody Reservation reservation);
	
	@GetMapping("/get/{id}")
	Reservation getById(@PathVariable("id") Long id);		
	
	@GetMapping(path = "/getAll", produces = "application/json")
	List<Reservation> getAll();
	
	@PostMapping("/update/{id}")
	void update(@PathVariable("id") long id, @RequestBody Reservation reservation);
	
	@DeleteMapping("/delete/{id}")
	void deleteById(@PathVariable("id") Long id);
	
	@DeleteMapping("/deleteAll")
	void deleteAll(); 
}
