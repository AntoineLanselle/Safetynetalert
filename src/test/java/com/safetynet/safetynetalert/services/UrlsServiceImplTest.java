package com.safetynet.safetynetalert.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.entities.modele2.Famille;
import com.safetynet.safetynetalert.entities.modele2.Personne;
import com.safetynet.safetynetalert.entities.response.ResponseChildAlert;
import com.safetynet.safetynetalert.entities.response.ResponseFire;

@ExtendWith(MockitoExtension.class)
public class UrlsServiceImplTest {

	@Mock
	private FirestationService firestationService;
	@Mock
	private PersonService personService;
	@Mock
	private MedicalrecordService medicalrecordService;
	@InjectMocks
	private UrlsServiceImpl urlsServiceImpl;

	static List<Person> listPerson;
	static List<Firestation> listFirestation;
	static List<Medicalrecord> listMedicalrecord;

	@BeforeAll
	public static void init() {
		listFirestation = new ArrayList<Firestation>();
		listPerson = new ArrayList<Person>();
		listMedicalrecord = new ArrayList<Medicalrecord>();

		// je créé 3 Firestation
		Firestation firestation1 = new Firestation();
		Firestation firestation2 = new Firestation();
		Firestation firestation3 = new Firestation();

		firestation1.setAddress("Address1");
		firestation1.setStation("Station1");

		firestation2.setAddress("Address1");
		firestation2.setStation("Station2");

		firestation3.setAddress("Address2");
		firestation3.setStation("Station2");

		listFirestation.add(firestation1);
		listFirestation.add(firestation2);
		listFirestation.add(firestation3);

		// je créé 3 Person
		Person person1 = new Person();
		Person person2 = new Person();
		Person person3 = new Person();

		person1.setFirstName("toto");
		person1.setLastName("tyty");
		person1.setAddress("Address1");
		person1.setCity("Culver");
		person1.setZip("zip");
		person1.setPhone("phone1");
		person1.setEmail("email1");

		person2.setFirstName("tutu");
		person2.setLastName("titi");
		person2.setAddress("Address1");
		person2.setCity("Culver");
		person2.setZip("zip");
		person2.setPhone("phone2");
		person2.setEmail("email2");

		person3.setFirstName("papa");
		person3.setLastName("mama");
		person3.setAddress("Address2");
		person3.setCity("Culver2");
		person3.setZip("zip2");
		person3.setPhone("phone3");
		person3.setEmail("email3");

		listPerson.add(person1);
		listPerson.add(person2);
		listPerson.add(person3);

		// je créé 3 Medicalrecord
		Medicalrecord medicalrecord1 = new Medicalrecord();
		Medicalrecord medicalrecord2 = new Medicalrecord();
		Medicalrecord medicalrecord3 = new Medicalrecord();
		List<String> allergieMedication = new ArrayList<String>();

		allergieMedication.add("allergie");
		allergieMedication.add("medication");

		medicalrecord1.setFirstName("toto");
		medicalrecord1.setLastName("tyty");
		medicalrecord1.setBirthdate("01/12/1997");
		medicalrecord1.setAllergies(allergieMedication);
		medicalrecord1.setMedications(allergieMedication);

		medicalrecord2.setFirstName("tutu");
		medicalrecord2.setLastName("titi");
		medicalrecord2.setBirthdate("01/12/2020");// mineur
		medicalrecord2.setAllergies(allergieMedication);
		medicalrecord2.setMedications(allergieMedication);

		medicalrecord3.setFirstName("papa");
		medicalrecord3.setLastName("mama");
		medicalrecord3.setBirthdate("01/12/2018");// mineur
		medicalrecord3.setAllergies(allergieMedication);
		medicalrecord3.setMedications(allergieMedication);

		listMedicalrecord.add(medicalrecord1);
		listMedicalrecord.add(medicalrecord2);
		listMedicalrecord.add(medicalrecord3);

	}

	@Test
	public void childAlert_ShouldReturnAResponseChildAlertObject() throws IOException, ParseException {
		// GIVEN
		ResponseChildAlert responseChildAlert = new ResponseChildAlert();
		when(personService.getPersons()).thenReturn(listPerson);
		when(medicalrecordService.getMedicalrecords()).thenReturn(listMedicalrecord);

		// WHEN
		responseChildAlert = urlsServiceImpl.childAlert("Address1");

		// THEN
		for (Personne personne : responseChildAlert.getAutresMembres()) {
			assertTrue(personne.getAge() > 18);
		}
		for (Personne personne : responseChildAlert.getEnfants()) {
			assertTrue(personne.getAge() <= 18);
		}
		assertEquals(1, responseChildAlert.enfants.size());
		assertEquals(1, responseChildAlert.autresMembres.size());

	}

	@Test
	public void phoneAlert_ShouldReturnListOfString() throws IOException, ParseException {
		// GIVEN
		List<String> listePhoneNumber = new ArrayList<String>();
		when(firestationService.getFirestations()).thenReturn(listFirestation);
		when(personService.getPersons()).thenReturn(listPerson);
		when(medicalrecordService.getMedicalrecords()).thenReturn(listMedicalrecord);

		// WHEN
		listePhoneNumber = urlsServiceImpl.phoneAlert("Station1");

		// THEN
		assertEquals(2, listePhoneNumber.size());

	}

	@Test
	public void fire_ShouldReturnAResponseFireObject() throws IOException, ParseException {
		// GIVEN
		ResponseFire response = new ResponseFire();
		List<String> listStation = new ArrayList<String>();
		listStation.add("Station2");
		listStation.add("Station1");
		when(firestationService.getFirestations()).thenReturn(listFirestation);
		when(personService.getPersons()).thenReturn(listPerson);
		when(medicalrecordService.getMedicalrecords()).thenReturn(listMedicalrecord);

		// WHEN
		response = urlsServiceImpl.fire("Address1");

		// THEN
		assertEquals(2, response.personnesVivantAddress.size());
		assertEquals(listStation, response.casernesDeservantAdress);

	}

	@Test
	public void floodStations_ShouldReturnListOfFamille() throws IOException, ParseException {
		// GIVEN
		List<Famille> listFamille = new ArrayList<Famille>();
		List<String> listStation = new ArrayList<String>();
		listStation.add("Station1");
		listStation.add("Station2");
		when(firestationService.getFirestations()).thenReturn(listFirestation);
		when(personService.getPersons()).thenReturn(listPerson);
		when(medicalrecordService.getMedicalrecords()).thenReturn(listMedicalrecord);

		// WHEN
		listFamille = urlsServiceImpl.floodStations(listStation);

		// THEN
		assertEquals(3, listFamille.size());

	}

	@Test
	public void personInfo_ShouldReturnListOfPersonne() throws IOException, ParseException {
		// GIVEN
		List<Personne> listPersonne = new ArrayList<Personne>();
		when(personService.getPersons()).thenReturn(listPerson);
		when(medicalrecordService.getMedicalrecords()).thenReturn(listMedicalrecord);

		// WHEN
		listPersonne = urlsServiceImpl.personInfo("toto", "tyty");

		// THEN
		assertEquals(1, listPersonne.size());
		assertEquals("toto", listPersonne.get(0).getFirstName());
		assertEquals("tyty", listPersonne.get(0).getLastName());

	}

	@Test
	public void communityEmail_ShouldReturnListOfString() throws IOException, ParseException {
		// GIVEN
		List<String> listEmail = new ArrayList<String>();
		when(personService.getPersons()).thenReturn(listPerson);

		// WHEN
		listEmail = urlsServiceImpl.communityEmail("Culver");

		// THEN
		assertEquals(2, listEmail.size());

	}

}
