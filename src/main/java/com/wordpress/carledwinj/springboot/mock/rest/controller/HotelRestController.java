package com.wordpress.carledwinj.springboot.mock.rest.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wordpress.carledwinj.springboot.mock.model.Hotel;
import com.wordpress.carledwinj.springboot.mock.service.HotelService;

@RestController
@RequestMapping(value="hotel")
public class HotelRestController {

	@Autowired
	private HotelService hotelService;
	
	@GetMapping
	public ResponseEntity getAll() {
		
		List<Hotel> hotels = hotelService.findAll();
		
		return ResponseEntity.ok(hotels);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable String id) {
		
		return ResponseEntity.ok().body(hotelService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody Hotel hotel) {
		
		Hotel savedHotel = hotelService.save(hotel);
		
		if(savedHotel == null) {
			return ResponseEntity.noContent().build();
		}
		
		URI uriLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/hotel/{id}").buildAndExpand(savedHotel.getId()).toUri(); 
		
		return ResponseEntity.created(uriLocation).build();
	}
	
	@PostMapping("/all")
	public ResponseEntity createAll(@RequestBody List<Hotel> hotels) {
		
		StringBuilder uriLocations = new StringBuilder("");
		List<Hotel> savedHotels = hotelService.saveAll(hotels);
			
		if(savedHotels != null) {
			
			for(Hotel hotel : savedHotels) {
				URI uriLocation = ServletUriComponentsBuilder.fromCurrentContextPath().path("/hotel/{id}").buildAndExpand(hotel.getId()).toUri();
				 uriLocations.append(uriLocation.toString()).append(", ");
			}
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(uriLocations);
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
