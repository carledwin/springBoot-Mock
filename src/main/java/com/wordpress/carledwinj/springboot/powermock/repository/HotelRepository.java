package com.wordpress.carledwinj.springboot.powermock.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wordpress.carledwinj.springboot.powermock.model.Hotel;

public interface HotelRepository extends MongoRepository<Hotel, String> {

	List<Hotel> findByName(String name);
}
