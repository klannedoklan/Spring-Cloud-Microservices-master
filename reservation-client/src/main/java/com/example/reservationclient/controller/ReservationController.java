package com.example.reservationclient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservationclient.entity.Reservation;
import com.example.reservationclient.feign.service.ReservationClientServiceFeign;



@RestController
@RequestMapping("/reservations/v2")
//@Import(FeignClientsConfiguration.class)
public class ReservationController extends BaseController{


	@Autowired
	ReservationClientServiceFeign resService;
	
	@PostMapping(path = "/add")
	public void add(@RequestBody Reservation reservation) {
		this.resService.add(reservation);		
	}

	@GetMapping("/get/{id}")
	public Reservation getById(@PathVariable("id") Long id) {
		return this.resService.getById(id);
	}

	@GetMapping(path = "/getAll", produces = "application/json")
	public List<Reservation> getAll() {
		return this.resService.getAll();
	}

	@PostMapping("/update/{id}")
	public void update(@PathVariable("id") long id, @RequestBody Reservation reservation) {
		this.resService.update(id, reservation);		
	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		this.resService.deleteById(id);
		
	}

	@DeleteMapping("/deleteAll")
	public void deleteAll() {
		this.resService.deleteAll();		
	}	
	
}
