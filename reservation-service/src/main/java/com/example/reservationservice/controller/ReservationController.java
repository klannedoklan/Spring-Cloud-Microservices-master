package com.example.reservationservice.controller;

import java.util.List;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservationservice.entity.CustomResponseModel;
import com.example.reservationservice.entity.Reservation;
import com.example.reservationservice.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	// repository direct call for simplicity only! Service should be implemented and
	// used
	@Autowired
	private ReservationRepository reservationRepository;

	
	@PostMapping("/add")
	@Produces({ "application/json", "application/xml" })
	@ResponseBody
	public ResponseEntity<?> addReservation(@RequestBody Reservation reservation) {
		ResponseEntity<?> response = new ResponseEntity<CustomResponseModel>(
				new CustomResponseModel(HttpStatus.BAD_REQUEST.value(),
						String.format("Reservation already exist: %s", reservation.getReservationName())),
				HttpStatus.BAD_REQUEST);

		if (this.reservationRepository.findByReservationName(reservation.getReservationName()).isEmpty()) {
			this.reservationRepository.save(reservation);
			response = new ResponseEntity<Reservation>(reservation, HttpStatus.CREATED);
		}

		return response;
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		ResponseEntity<?> response = new ResponseEntity<CustomResponseModel>(
				new CustomResponseModel(HttpStatus.BAD_REQUEST.value(),
						String.format("Reservation with id %d not found", id)),
				HttpStatus.BAD_REQUEST);
		Reservation reservation = this.reservationRepository.findOne(id);
		if (reservation != null) {
			response = new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
		}
		return response;
	}

	
	@GetMapping(path = "/getAll", produces = { "application/xml", "text/xml",
			"application/json" }, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> getAll() {
		ResponseEntity<?> response = new ResponseEntity<CustomResponseModel>(
				new CustomResponseModel(HttpStatus.BAD_REQUEST.value(), String.format("Reservations are empty")),
				HttpStatus.BAD_REQUEST);

		List<Reservation> allReservations = this.reservationRepository.findAll();
		if (!allReservations.isEmpty()) {
			response = new ResponseEntity<List<Reservation>>(allReservations, HttpStatus.OK);
		}
		return response;
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Reservation reservation) {
		Reservation currentReservation = null;
		ResponseEntity<?> response = new ResponseEntity<CustomResponseModel>(
				new CustomResponseModel(HttpStatus.BAD_REQUEST.value(),
						String.format("Reservation with id = %d not found!", id)),
				HttpStatus.BAD_REQUEST);
		try {
			currentReservation = this.reservationRepository.findOne(id);			
		}catch(Exception e) {
			System.out.println(e.getMessage()); 			
		}			
		if (currentReservation != null) {
			currentReservation.setReservationName(reservation.getReservationName());
			this.reservationRepository.saveAndFlush(currentReservation);
			//has body
			response = new ResponseEntity<String>(String.format("Reservation: %d successfully updated.", id),
					HttpStatus.OK);
		}
		return response;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CustomResponseModel> deleteById(@PathVariable("id") Long id) {
		ResponseEntity<CustomResponseModel> response = new ResponseEntity<>(
				new CustomResponseModel(HttpStatus.BAD_REQUEST.value(),
						String.format("Reservation with id = %d not found!", id)),
				HttpStatus.BAD_REQUEST);
		Reservation reservation = this.reservationRepository.findOne(id);
		if (reservation != null) {
			//delete does not have body!
			response = new ResponseEntity<>(
					new CustomResponseModel(HttpStatus.OK.value(),
							String.format("Reservation: %d successfully deleted.", id)),
					HttpStatus.OK);	
					
		}
		return response;
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<CustomResponseModel> deleteAll() {
		ResponseEntity<CustomResponseModel> response = new ResponseEntity<>(
				new CustomResponseModel(HttpStatus.BAD_REQUEST.value(),
						String.format("Reservations are empty - Action cancelled.")),
				HttpStatus.BAD_REQUEST);
		if (this.reservationRepository.count() > 0) {
			this.reservationRepository.deleteAll();
			;
			response = new ResponseEntity<>(
					new CustomResponseModel(HttpStatus.OK.value(),
							String.format("All reservations deleted.")),
					HttpStatus.OK);				
					
		}
		return response;
	}

}
