package com.safetynet.safetynetalert.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.response.ResponseGetFirestation;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.services.FirestationService;

@ExtendWith(MockitoExtension.class)
public class FirestationControllerTest {

	@Mock
	private FirestationService firestationService;
	@InjectMocks
	private FirestationController firestationController;

	@Test
	public void getAllFirestations_ShouldReturnListOfFirestationWithStatusOK() {
		// GIVEN
		List<Firestation> listFirestations = new ArrayList<Firestation>();
		Firestation firestation = new Firestation();
		firestation.setAddress("toto");
		firestation.setStation("tutu");
		listFirestations.add(firestation);

		// WHEN
		when(firestationService.getFirestations()).thenReturn(listFirestations);
		ResponseEntity<List<Firestation>> result = firestationController.getAllFirestations();

		// THEN
		assertEquals(result.getBody().get(0).getClass(), firestation.getClass());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void getFirestation_ShouldReturnResponseGetFirestationWithStatusOK() throws ParseException {
		// GIVEN
		ResponseGetFirestation response = new ResponseGetFirestation();
		when(firestationService.getFirestation("Station1")).thenReturn(response);

		// WHEN
		ResponseEntity<MappingJacksonValue> result = firestationController.getFirestation("Station1");

		// THEN
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody().getValue());
	}

	@Test
	public void addFirestation_ShouldAddFirestationWithStatusOK() throws AlreadyExistException {
		// GIVEN
		Firestation firestation = new Firestation();
		firestation.setAddress("toto");
		firestation.setStation("tutu");

		// WHEN
		ResponseEntity<String> result = firestationController.addFirestation(firestation);

		// THEN
		assertEquals(result.getBody(),
				"La firestation " + firestation.getStation() + " " + firestation.getAddress() + " a bien été ajoutée");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void updateFirestation_ShouldUpdateFirestationWithStatusOK()
			throws RessourceNotFoundException, AlreadyExistException {
		// GIVEN
		String station = "Station";
		String address = "addressn";
		String newStation = "newStation";

		// WHEN
		ResponseEntity<String> result = firestationController.updateFirestation(station, address, newStation);

		// THEN
		assertEquals(result.getBody(),
				"Le numero de station à l'address: " + address + " est maintenant: " + newStation);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void deleteFirestation_ShouldDeleteFirestationUsingStationAndAddressWithStatusOK()
			throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> result = firestationController.deleteFirestation("station", "address");

		// THEN
		assertEquals(result.getBody(), "La Station: station et l'Address: address ont bien été supprimés");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void deleteFirestation_ShouldDeleteFirestationUsingStationWithStatusOK()
			throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> result = firestationController.deleteFirestation("station", null);

		// THEN
		assertEquals(result.getBody(), "La Station: station a bien été supprimée");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void deleteFirestation_ShouldDeleteFirestationUsingAddressWithStatusOK()
			throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> result = firestationController.deleteFirestation(null, "address");

		// THEN
		assertEquals(result.getBody(), "L'Address: address a bien été supprimée");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void deleteFirestation_ShouldDeleteFirestationUsingNullWithStatusOK()
			throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> result = firestationController.deleteFirestation(null, null);

		// THEN
		assertEquals(result.getBody(), "Veuillez spécifié une station, une address ou les deux");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
