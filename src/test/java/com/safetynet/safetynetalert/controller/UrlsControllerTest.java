package com.safetynet.safetynetalert.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.safetynet.safetynetalert.entities.modele2.Famille;
import com.safetynet.safetynetalert.entities.modele2.Personne;
import com.safetynet.safetynetalert.entities.response.ResponseChildAlert;
import com.safetynet.safetynetalert.entities.response.ResponseFire;
import com.safetynet.safetynetalert.services.UrlsService;

@ExtendWith(MockitoExtension.class)
public class UrlsControllerTest {

	@Mock
	private UrlsService urlsService;

	@InjectMocks
	private UrlsController urlsController;

	@Test
	public void childAlert_ShouldReturnMappingJacksonValueWithStatusOK() throws IOException, ParseException {
		ResponseChildAlert response = new ResponseChildAlert();
		when(urlsService.childAlert("Address1")).thenReturn(response);

		// WHEN
		ResponseEntity<MappingJacksonValue> result = urlsController.childAlert("Address1");

		// THEN
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody().getValue());
	}
	
	@Test
	public void phoneAlert_ShouldReturnMappingJacksonValueWithStatusOK() throws IOException, ParseException {
		List<String> response = new ArrayList<String>();
		when(urlsService.phoneAlert("Station1")).thenReturn(response);

		// WHEN
		ResponseEntity<MappingJacksonValue> result = urlsController.phoneAlert("Station1");

		// THEN
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody().getValue());
	}

	@Test
	public void fire_ShouldReturnMappingJacksonValueWithStatusOK() throws IOException, ParseException {
		ResponseFire response = new ResponseFire();
		when(urlsService.fire("Address1")).thenReturn(response);

		// WHEN
		ResponseEntity<MappingJacksonValue> result = urlsController.fire("Address1");

		// THEN
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody().getValue());
	}
	
	@Test
	public void floodStation_ShouldReturnMappingJacksonValueWithStatusOK() throws IOException, ParseException {
		List<Famille> response = new ArrayList<Famille>();
		List<String> listStations = new ArrayList<String>();
		when(urlsService.floodStations(listStations)).thenReturn(response);

		// WHEN
		ResponseEntity<MappingJacksonValue> result = urlsController.floodStations(listStations);

		// THEN
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody().getValue());
	}
	
	@Test
	public void personInfo_ShouldReturnMappingJacksonValueWithStatusOK() throws IOException, ParseException {
		List<Personne> response = new ArrayList<Personne>();
		when(urlsService.personInfo("toto", "titi")).thenReturn(response);

		// WHEN
		ResponseEntity<MappingJacksonValue> result = urlsController.personInfo("toto", "titi");

		// THEN
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody().getValue());
	}
	
	@Test
	public void communityEmail_ShouldReturnMappingJacksonValueWithStatusOK() throws IOException, ParseException {
		List<String> response = new ArrayList<String>();
		when(urlsService.communityEmail("Culver")).thenReturn(response);

		// WHEN
		ResponseEntity<MappingJacksonValue> result = urlsController.communityEmail("Culver");

		// THEN
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody().getValue());
	}
	
}
