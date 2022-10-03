package com.safetynet.safetynetalert.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.repository.MedicalrecordRepository;

@ExtendWith(MockitoExtension.class)
public class MedicalrecordServiceImplTest {

	@Mock
	private MedicalrecordRepository medicalrecordRepository;
	@InjectMocks
	private MedicalrecordServiceImpl medicalrecordServiceImpl;

	private static List<Medicalrecord> listMedicalrecords;

	@BeforeEach
	public void init() {
		// j'ajoute deux Person dans ma liste
		listMedicalrecords = new ArrayList<Medicalrecord>();
		Medicalrecord medicalrecord1 = new Medicalrecord();
		Medicalrecord medicalrecord2 = new Medicalrecord();
		medicalrecord1.setFirstName("toto");
		medicalrecord1.setLastName("tyty");
		medicalrecord2.setFirstName("tutu");
		medicalrecord2.setLastName("titi");
		listMedicalrecords.add(medicalrecord1);
		listMedicalrecords.add(medicalrecord2);
	}

	@Test
	public void getMedicalrecords_ShouldReturnListOfMedicalrecord() {
		// GIVEN
		when(medicalrecordRepository.getAllMedicalrecords()).thenReturn(listMedicalrecords);

		// WHEN

		// THEN
		assertEquals(listMedicalrecords, medicalrecordServiceImpl.getMedicalrecords());
	}

	@Test
	public void addMedicalrecord_ShouldAddMedicalrecordInParamaterInList() throws AlreadyExistException {
		// GIVEN
		Medicalrecord newMedicalrecord = new Medicalrecord();
		newMedicalrecord.setFirstName("Antoine");
		newMedicalrecord.setLastName("Lanselle");

		when(medicalrecordRepository.findByName(newMedicalrecord.getFirstName(), newMedicalrecord.getLastName()))
				.thenReturn(new Medicalrecord());
		when(medicalrecordRepository.addMedicalrecord(newMedicalrecord))
				.thenReturn(listMedicalrecords.add(newMedicalrecord));

		// WHEN
		medicalrecordServiceImpl.addMedicalrecord(newMedicalrecord);

		// THEN
		assertTrue(listMedicalrecords.contains(newMedicalrecord));
	}

	@Test
	public void addMedicalrecord_ShouldThrowAlreadyExistException() throws AlreadyExistException {
		// GIVEN
		Medicalrecord medicalrecord = new Medicalrecord();
		medicalrecord.setFirstName("Antoine");
		medicalrecord.setLastName("Lanselle");

		when(medicalrecordRepository.findByName(medicalrecord.getFirstName(), medicalrecord.getLastName()))
				.thenReturn(medicalrecord);

		// WHEN

		// THEN
		assertThrows(AlreadyExistException.class, () -> {
			medicalrecordServiceImpl.addMedicalrecord(medicalrecord);
		});
	}

	@Test
	public void updateMedicalrecord_ShouldUpdateMedicalrecordInParamaterInList() throws RessourceNotFoundException {
		// GIVEN
		Medicalrecord medicalrecord = new Medicalrecord();
		medicalrecord.setFirstName("toto");
		medicalrecord.setLastName("tyty");
		medicalrecord.setBirthdate("12/12/12");

		when(medicalrecordRepository.findByName(medicalrecord.getFirstName(), medicalrecord.getLastName()))
				.thenReturn(listMedicalrecords.get(0));

		// WHEN
		medicalrecordServiceImpl.updateMedicalrecord(medicalrecord);

		// THEN
		assertEquals(medicalrecord.getBirthdate(), listMedicalrecords.get(0).getBirthdate());
	}

	@Test
	public void updateMedicalrecord_ShouldThrowRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		when(medicalrecordRepository.findByName(null, null)).thenReturn(new Medicalrecord());

		// WHEN

		// THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			medicalrecordServiceImpl.updateMedicalrecord(new Medicalrecord());
		});
	}

	@Test
	public void deleteMedicalrecord_ShouldDeleteMedicalrecordInParamaterInList() throws RessourceNotFoundException {
		// GIVEN
		when(medicalrecordRepository.findByName("toto", "tyty")).thenReturn(listMedicalrecords.get(0));

		// WHEN
		medicalrecordServiceImpl.deleteMedicalrecord("toto", "tyty");

		// THEN
		verify(medicalrecordRepository, times(1)).delete(any(Medicalrecord.class));
	}

	@Test
	public void deleteMedicalrecord_ShouldThrowRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		when(medicalrecordRepository.findByName(null, null)).thenReturn(new Medicalrecord());

		// WHEN

		// THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			medicalrecordServiceImpl.deleteMedicalrecord(null, null);
		});
	}

}
