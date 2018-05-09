package com.wordpress.carledwinj.springboot.powermock.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordpress.carledwinj.springboot.powermock.model.Hotel;
import com.wordpress.carledwinj.springboot.powermock.service.HotelService;

@RestController
@RequestMapping(value="hotel")
public class HotelRestController {

	@Autowired
	private HotelService hotelService;
	
	@GetMapping("/all")
	public ResponseEntity getAll() {
		
		List<Hotel> hotels = hotelService.findAll();
		
		return ResponseEntity.ok(hotels);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable String id) {
		
		return ResponseEntity.ok().body(hotelService.findById(id));
	}
	
	@PostMapping("/create")
	public ResponseEntity create(@RequestBody Hotel hotel) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.save(hotel));
	}
	
	@PostMapping("/create-all")
	public ResponseEntity createAll(@RequestBody List<Hotel> hotels) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.saveAll(hotels));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable String id, @RequestBody Hotel hotel) {
		
		if(hotel != null) {
			hotel.setId(id);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(hotelService.update(hotel));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable String id) {
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hotelService.delete(id));
	}
	
}
