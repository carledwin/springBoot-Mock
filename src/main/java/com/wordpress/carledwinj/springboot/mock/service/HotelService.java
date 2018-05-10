package com.wordpress.carledwinj.springboot.mock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wordpress.carledwinj.springboot.mock.model.Hotel;
import com.wordpress.carledwinj.springboot.mock.repository.HotelRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	public List<Hotel> findAll(){
		return hotelRepository.findAll();
	}
	
	public List<Hotel> findByName(String name){
		return hotelRepository.findByName(name);
	}
	
	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	
	public List<Hotel> saveAll(List<Hotel> hotels) {
		return hotelRepository.saveAll(hotels);
	}
	
	public boolean update(Hotel hotel) {
		
		if(!StringUtils.isEmpty(hotel.getId()) && !StringUtils.isEmpty(hotel.getName())) {
			
			if(hotelRepository.existsById(hotel.getId())) {
				
				Hotel hotelUpdate = hotelRepository.findById(hotel.getId()).get();
				
				hotelUpdate.setName(hotel.getName());
				
				return null != hotelRepository.save(hotel);
			}
		}
		
		return false;
	}
	
	public boolean delete(String id) {
		
		if(hotelRepository.existsById(id)) {
			
			hotelRepository.deleteById(id);
			
			if(!hotelRepository.existsById(id)) {
				return true;
			}
			
			return false;
		}
		
		return false;
	}

	public Hotel findById(String id) {

		return hotelRepository.findById(id).get();
	}
}
