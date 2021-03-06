package com.wordpress.carledwinj.springboot.mock.springtest.rest.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
	
	String mockHotelBodyRequestPostJson = "{\"id\":\"66565\", \"name\":\"New Hotel Mock\"}";
	String mockHotelsBodyRequestPostJson = "[{\"id\":\"666\", \"name\":\"New Hotel 6 Mock\"},{\"id\":\"777\", \"name\":\"New Hotel 7 Mock\"}, {\"id\":\"888\", \"name\":\"New Hotel 7 Mock\"}, {\"id\":\"999\", \"name\":\"New Hotel 9 Mock\"}]";
	
	String responseExpected = "{\"id\":\"32334232\", \"name\":\"Mock 1 Hotel\"}";
	String responseExpectedGetAll = "[{\"id\":\"1111\", \"name\":\"Mock 1 Hotel\"}, {\"id\":\"2222\", \"name\":\"Mock 2 Hotel\"}, {\"id\":\"3333\", \"name\":\"Mock 3 Hotel\"}]";
	
	private List<Hotel> mockHotels = java.util.Arrays.asList(new Hotel("1111", "Mock 1 Hotel"), new Hotel("2222", "Mock 2 Hotel"), new Hotel("3333", "Mock 3 Hotel"));
	private List<Hotel> mockNewHotels = java.util.Arrays.asList(new Hotel("666", "New Hotel 6 Mock"), new Hotel("777", "New Hotel 7 Mock"), new Hotel("888", "New Hotel 8 Mock"), new Hotel("999", "New Hotel 9 Mock"));
	
	private String resultPostAll = "http://localhost/hotel/666, http://localhost/hotel/777, http://localhost/hotel/888, http://localhost/hotel/999, ";
	
	@Test
	public void getAll() throws Exception{
		
		Mockito.when(hotelService.findAll()).thenReturn(mockHotels);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hotel").accept(CONTENT_TYPE_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		LOGGER.info("GET ALL Result: " + mvcResult.getResponse().getContentAsString());
		JSONAssert.assertEquals(responseExpectedGetAll, mvcResult.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getById() throws Exception{
		
		Mockito.when(hotelService.findById(Mockito.anyString())).thenReturn(mockHotel);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hotel/32334232") .accept(CONTENT_TYPE_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		LOGGER.info("GET Result: " + mvcResult.getResponse().getContentAsString());
		JSONAssert.assertEquals(responseExpected, mvcResult.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void post() throws Exception{
		
		Mockito.when(hotelService.save(Mockito.any(Hotel.class))).thenReturn(mockNewHotel);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hotel").accept(CONTENT_TYPE_JSON) .content(mockHotelBodyRequestPostJson).contentType(CONTENT_TYPE_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		LOGGER.info("POST Status: " + mockHttpServletResponse.getStatus());
		assertEquals(HttpStatus.CREATED.value(), mockHttpServletResponse.getStatus());
		
		LOGGER.info("POST Location: " + mockHttpServletResponse.getHeader(HttpHeaders.LOCATION));
		assertEquals("http://localhost/hotel/66565", mockHttpServletResponse.getHeader(HttpHeaders.LOCATION));
	}
	
	@Test
	public void postAll() throws Exception{
		
		Mockito.when(hotelService.saveAll(Mockito.any(List.class))).thenReturn(mockNewHotels);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hotel/all").accept(CONTENT_TYPE_JSON).content(mockHotelsBodyRequestPostJson).contentType(CONTENT_TYPE_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		LOGGER.info("POST ALL Status: " + mockHttpServletResponse.getStatus());
		assertEquals(HttpStatus.CREATED.value(), mockHttpServletResponse.getStatus());
		
		LOGGER.info("POST ALL Locations: " + mockHttpServletResponse.getHeader(HttpHeaders.LOCATION));
		assertEquals(resultPostAll, mvcResult.getResponse().getContentAsString());
	}
}
