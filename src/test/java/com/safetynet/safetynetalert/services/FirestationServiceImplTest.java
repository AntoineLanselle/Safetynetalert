package com.safetynet.safetynetalert.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.entities.modele2.Personne;
import com.safetynet.safetynetalert.entities.response.ResponseGetFirestation;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.repository.FirestationRepository;
import com.safetynet.safetynetalert.repository.MedicalrecordRepository;
import com.safetynet.safetynetalert.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceImplTest {

	@Mock
	private FirestationRepository firestationRepository;
	@Mock
	private PersonRepository personRepository;
	@Mock
	private MedicalrecordRepository medicalrecordRepository;
	@InjectMocks
	private FirestationServiceImpl firestationServiceImpl;

	private static List<Firestation> listFirestations;

	@BeforeEach
	public void init() {
		listFirestations = new ArrayList<Firestation>();
		Firestation firestation1 = new Firestation();
		Firestation firestation2 = new Firestation();
		Firestation firestation3 = new Firestation();
		firestation1.setAddress("Address1");
		firestation1.setStation("Station1");
		listFirestations.add(firestation1);
		firestation2.setAddress("Address1");
		firestation2.setStation("Station2");
		listFirestations.add(firestation2);
		firestation3.setAddress("Address2");
		firestation3.setStation("Station2");
		listFirestations.add(firestation3);
	}

	@Test
	public void getFirestations_ShouldReturnListOfFirestation() {
		// GIVEN
		when(firestationRepository.getAllFirestations()).thenReturn(listFirestations);

		// WHEN

		// THEN
		assertEquals(listFirestations, firestationServiceImpl.getFirestations());
	}

	@Test
	public void getFirestation_ShouldReturnAResponseGetFirestationObject() throws ParseException {
		// GIVEN
		ResponseGetFirestation response = new ResponseGetFirestation();
		List<Person> listPersons = new ArrayList<Person>();
		List<Medicalrecord> listMedicalrecords = new ArrayList<Medicalrecord>();
		List<Personne> listePersonneCouvertes = new ArrayList<Personne>();

		Medicalrecord medicalrecord1 = new Medicalrecord();
		Medicalrecord medicalrecord2 = new Medicalrecord();
		List<String> medicationsAndAllergies = new ArrayList<String>();
		medicalrecord1.setFirstName("toto");
		medicalrecord2.setFirstName("tutu");
		medicalrecord1.setLastName("tyty");
		medicalrecord2.setLastName("titi");
		medicalrecord1.setBirthdate("01/12/1997");
		medicalrecord2.setBirthdate("10/20/2020");
		medicalrecord1.setAllergies(medicationsAndAllergies);
		medicalrecord2.setAllergies(medicationsAndAllergies);
		medicalrecord1.setMedications(medicationsAndAllergies);
		medicalrecord2.setMedications(medicationsAndAllergies);
		listMedicalrecords.add(medicalrecord1);
		listMedicalrecords.add(medicalrecord2);

		Person person1 = new Person();
		Person person2 = new Person();
		person1.setFirstName("toto");
		person2.setFirstName("tutu");
		person1.setLastName("tyty");
		person2.setLastName("titi");
		person1.setAddress("Address1");
		person2.setAddress("Address2");
		listPersons.add(person2);
		listPersons.add(person1);

		Personne personne1 = new Personne(person1, listMedicalrecords);
		Personne personne2 = new Personne(person2, listMedicalrecords);
		listePersonneCouvertes.add(personne1);
		listePersonneCouvertes.add(personne2);

		response.setNombreAdultes(1);
		response.setNombreEnfants(1);
		response.setPersonnesCouvertes(listePersonneCouvertes);

		when(firestationRepository.getAllFirestations()).thenReturn(listFirestations);
		when(personRepository.getAllPersons()).thenReturn(listPersons);
		when(medicalrecordRepository.getAllMedicalrecords()).thenReturn(listMedicalrecords);

		// WHEN
		ResponseGetFirestation response2 = firestationServiceImpl.getFirestation("Station2");

		// THEN
		assertEquals(1, response2.getNombreEnfants());
		assertEquals(1, response2.getNombreAdultes());
		assertEquals(response.getPersonnesCouvertes().get(0).getFirstName(),
				response2.getPersonnesCouvertes().get(0).getFirstName());
		assertEquals(response.getPersonnesCouvertes().get(0).getLastName(),
				response2.getPersonnesCouvertes().get(0).getLastName());
		assertEquals(response.getPersonnesCouvertes().get(1).getFirstName(),
				response2.getPersonnesCouvertes().get(1).getFirstName());
		assertEquals(response.getPersonnesCouvertes().get(1).getLastName(),
				response2.getPersonnesCouvertes().get(1).getLastName());
	}

	@Test
	public void addFirestation_ShouldAddFirestationInParamaterInList() throws AlreadyExistException {
		// GIVEN
		Firestation newFirestation = new Firestation();
		newFirestation.setStation("newStation");
		newFirestation.setAddress("newAddress");

		when(firestationRepository.findStationAddress(newFirestation.getStation(), newFirestation.getAddress()))
				.thenReturn(new Firestation());
		when(firestationRepository.addFirestation(newFirestation)).thenReturn(listFirestations.add(newFirestation));

		// WHEN
		firestationServiceImpl.addFirestation(newFirestation);

		// THEN
		assertTrue(listFirestations.contains(newFirestation));
	}

	@Test
	public void addFirestation_ShouldthrowAlreadyExistException() throws AlreadyExistException {
		// GIVEN
		Firestation newFirestation = new Firestation();
		newFirestation.setStation("newStation");
		newFirestation.setAddress("newAddress");

		when(firestationRepository.findStationAddress(newFirestation.getStation(), newFirestation.getAddress()))
				.thenReturn(newFirestation);

		// WHEN

		// THEN
		assertThrows(AlreadyExistException.class, () -> {
			firestationServiceImpl.addFirestation(newFirestation);
		});
	}

	@Test
	public void updateFirestation_ShouldUpdateFirestationInParamaterInList()
			throws RessourceNotFoundException, AlreadyExistException {
		// GIVEN
		when(firestationRepository.findStationAddress("Station1", "Address1")).thenReturn(listFirestations.get(0));
		when(firestationRepository.findStationAddress("newStation", "Address1")).thenReturn(new Firestation());

		// WHEN
		firestationServiceImpl.updateFirestation("Station1", "Address1", "newStation");

		// THEN
		assertEquals("newStation", listFirestations.get(0).getStation());
	}

	@Test
	public void updateFirestation_ShouldThrowRessourceNotFoundException()
			throws RessourceNotFoundException, AlreadyExistException {
		// GIVEN
		when(firestationRepository.findStationAddress("station", "address")).thenReturn(new Firestation());

		// WHEN

		// THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			firestationServiceImpl.updateFirestation("station", "address", "newStation");
		});
	}

	@Test
	public void updateFirestation_ShouldThrowAlreadyExistException()
			throws RessourceNotFoundException, AlreadyExistException {
		// GIVEN
		when(firestationRepository.findStationAddress("station", "address")).thenReturn(listFirestations.get(0));
		when(firestationRepository.findStationAddress("newStation", "address")).thenReturn(listFirestations.get(0));

		// WHEN

		// THEN
		assertThrows(AlreadyExistException.class, () -> {
			firestationServiceImpl.updateFirestation("station", "address", "newStation");
		});
	}

	@Test
	public void deleteStation_ShouldDeleteStationInParameterInList() throws RessourceNotFoundException {
		// GIVEN
		when(firestationRepository.findByStation("Station1")).thenReturn(listFirestations.subList(0, 1));

		// WHEN
		firestationServiceImpl.deleteStation("Station1");

		// THEN
		verify(firestationRepository, times(1)).delete(any(Firestation.class));

	}
	
	@Test
	public void deleteAddress_ShouldDeleteAddressInParameterInList() throws RessourceNotFoundException {
		// GIVEN
		when(firestationRepository.findByAddress("Address2")).thenReturn(listFirestations.subList(2, 3));

		// WHEN
		firestationServiceImpl.deleteAddress("Address2");

		// THEN
		verify(firestationRepository, times(1)).delete(any(Firestation.class));

	}

	@Test
	public void deleteStation_ShouldThrowRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		when(firestationRepository.findByStation("Station1")).thenReturn(new ArrayList<Firestation>());

		// WHEN

		// THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			firestationServiceImpl.deleteStation("Station1");
		});
	}

	@Test
	public void deleteAddress_ShouldThrowRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		when(firestationRepository.findByAddress("Address2")).thenReturn(new ArrayList<Firestation>());

		// WHEN

		// THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			firestationServiceImpl.deleteAddress("Address2");
		});
	}

}
