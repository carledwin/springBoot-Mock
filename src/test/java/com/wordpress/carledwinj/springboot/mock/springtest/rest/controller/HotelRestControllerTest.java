package com.wordpress.carledwinj.springboot.mock.springtest.rest.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.wordpress.carledwinj.springboot.mock.model.Hotel;
import com.wordpress.carledwinj.springboot.mock.rest.controller.HotelRestController;
import com.wordpress.carledwinj.springboot.mock.service.HotelService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HotelRestController.class, secure=false)
public class HotelRestControllerTest {

	private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON;

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HotelService hotelService;
	
	Hotel mockHotel = new Hotel("32334232", "Mock 1 Hotel");
	Hotel mockNewHotel = new Hotel("66565","New Hotel mock");
	
	String mockHotelBodyRequestPostJson = "{\"id\":\"66565\", \"name\":\"New Hotel mock\"}";
	
	@Test
	public void restrieveDetailsForHotel() throws Exception{
		
		Mockito.when(hotelService.findById(Mockito.anyString())).thenReturn(mockHotel);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hotel/32334232") .accept(CONTENT_TYPE_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		LOGGER.info(mvcResult.getResponse().getContentAsString());
		
		String responseExpected = "{\"id\":\"32334232\", \"name\":\"Mock 1 Hotel\"}";
		
		JSONAssert.assertEquals(responseExpected, mvcResult.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void createHotel() throws Exception{
		
		Mockito.when(hotelService.save(Mockito.any(Hotel.class))).thenReturn(mockNewHotel);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hotel/create").accept(CONTENT_TYPE_JSON) .content(mockHotelBodyRequestPostJson).contentType(CONTENT_TYPE_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), mockHttpServletResponse.getStatus());
		assertEquals("http://localhost/hotel/66565", mockHttpServletResponse.getHeader(HttpHeaders.LOCATION));
	}
	
}
