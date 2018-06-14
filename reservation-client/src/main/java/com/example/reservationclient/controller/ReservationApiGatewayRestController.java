package com.example.reservationclient.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.reservationclient.entity.Reservation;
import com.example.reservationclient.service.ReservationClientService;
import com.example.reservationclient.entity.CustomResponseModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@RestController
public class ReservationApiGatewayRestController extends BaseController {

	@Value("${reservationclient.root_uri}")
	private String ROOT_URI;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ReservationClientService reservationClientService;

	// @GetMapping("/names")
	// public Collection<String> getReservationNames() {
	// final ParameterizedTypeReference<Resources<Reservation>> ptr = new
	// ParameterizedTypeReference<Resources<Reservation>>() {
	// };
	// final ResponseEntity<Resources<Reservation>> resposneEntity =
	// this.restTemplate.exchange(ROOT_URI,
	// HttpMethod.GET, null, ptr);
	// return
	// resposneEntity.getBody().getContent().stream().map(Reservation::getReservationName)
	// .collect(Collectors.toList());
	//
	// }

	@GetMapping("/v1/getAll")
	public ResponseEntity<?> getAllReservations() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> resposneEntity = null;
		try {
			resposneEntity = this.reservationClientService.exchange(ROOT_URI + "/getAll", HttpMethod.GET,
					new HashMap<>(), null);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<String>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
		}

		TypeFactory typeFactory = objectMapper.getTypeFactory();
		List<Reservation> allReservations = objectMapper.readValue(resposneEntity.getBody(),
				typeFactory.constructCollectionType(List.class, Reservation.class));
		return new ResponseEntity<List<Reservation>>(allReservations, HttpStatus.OK);
	}

	@GetMapping("/v1/get/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id)
			throws JsonParseException, JsonMappingException, IOException, HttpClientErrorException {
		ResponseEntity<String> resposneEntity = null;

		try {
			resposneEntity = this.reservationClientService.exchange(ROOT_URI + "/get/" + id, HttpMethod.GET,
					new HashMap<>(), null);

		} catch (HttpClientErrorException e) {
			return new ResponseEntity<String>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
		}

		Reservation reservation = objectMapper.readValue(resposneEntity.getBody(), Reservation.class);
		return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
	}

	@PostMapping(value = "/v1/add", consumes = "application/json")
	public ResponseEntity<String> addReservation(@RequestBody Reservation reservation) {
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = this.reservationClientService.exchange(ROOT_URI + "/add", HttpMethod.POST, new HashMap<>(),
					reservation);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<String>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.CREATED);
	}

	@PostMapping(value = "/v1/update/{id}", consumes = "application/json")
	public ResponseEntity<?> updateReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = this.reservationClientService.exchange(ROOT_URI + "/update/" + id, HttpMethod.POST,
					new HashMap<>(), reservation);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<String>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
		}
		
		responseEntity = new ResponseEntity<CustomResponseModel>(new CustomResponseModel(HttpStatus.OK.value(),
				String.format("Reservation: %d successfully updated.", id)), HttpStatus.OK);
		return responseEntity; 
	}

	@DeleteMapping("/v1/delete/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable("id") Long id) {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = this.reservationClientService.exchange(ROOT_URI + "/delete/" + id, HttpMethod.DELETE,
					new HashMap<>(), null);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<String>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
		}
		responseEntity = new ResponseEntity<CustomResponseModel>(new CustomResponseModel(HttpStatus.OK.value(),
				String.format("Reservation: %d successfully deleted.", id)), HttpStatus.OK);

		return responseEntity;
	}

}
