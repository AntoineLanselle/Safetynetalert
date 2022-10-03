package com.safetynet.safetynetalert.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.services.MedicalrecordService;

@ExtendWith(MockitoExtension.class)
public class MedicalrecordControllerTest {

	@Mock
	private MedicalrecordService medicalrecordService;

	@InjectMocks
	private MedicalrecordController medicalrecordController;

	@Test
	public void getAllMedicalrecords_ShouldReturnListOfMedicalrecordsWithStatusOK() {
		// GIVEN
		List<Medicalrecord> listMedicalrecords = new ArrayList<Medicalrecord>();
		Medicalrecord medicalrecord = new Medicalrecord();
		medicalrecord.setFirstName("toto");
		medicalrecord.setLastName("tutu");
		listMedicalrecords.add(medicalrecord);
		when(medicalrecordService.getMedicalrecords()).thenReturn(listMedicalrecords);

		// WHEN
		ResponseEntity<List<Medicalrecord>> result = medicalrecordController.getAllMedicalrecords();

		// THEN
		assertEquals(result.getBody().get(0).getClass(), medicalrecord.getClass());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void addMedicalrecord_ShouldAddMedicalrecordWithStatusOK() throws AlreadyExistException {
		// GIVEN
		Medicalrecord medicalrecord = new Medicalrecord();
		medicalrecord.setFirstName("toto");
		medicalrecord.setLastName("tutu");

		// WHEN
		ResponseEntity<String> result = medicalrecordController.addMedicalrecord(medicalrecord);

		// THEN
		assertEquals(result.getBody(), "Le dossier medical de " + medicalrecord.getFirstName() + " "
				+ medicalrecord.getLastName() + " a bien été créé");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void updateMedicalrecord_ShouldUpdateMedicalrecordWithStatusOK() throws RessourceNotFoundException {
		// GIVEN
		Medicalrecord medicalrecord = new Medicalrecord();
		medicalrecord.setFirstName("toto");
		medicalrecord.setLastName("tutu");

		// WHEN
		ResponseEntity<String> result = medicalrecordController.updateMedicalrecord(medicalrecord);

		// THEN
		assertEquals(result.getBody(), "Le dossier medical de " + medicalrecord.getFirstName() + " "
				+ medicalrecord.getLastName() + " a bien été modifié");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void deleteMedicalrecord_ShouldDeleteMedicalrecordWithStatusOK() throws RessourceNotFoundException {
		// GIVEN
		Medicalrecord medicalrecord = new Medicalrecord();
		medicalrecord.setFirstName("toto");
		medicalrecord.setLastName("tutu");

		// WHEN
		ResponseEntity<String> result = medicalrecordController.deleteMedicalrecord(medicalrecord.getFirstName(),
				medicalrecord.getLastName());

		// THEN
		assertEquals(result.getBody(), "Le dossier medical de " + medicalrecord.getFirstName() + " "
				+ medicalrecord.getLastName() + " a bien été supprimé");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
