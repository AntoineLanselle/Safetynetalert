package com.safetynet.safetynetalert.entities.modele2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.entities.modele1.Person;

public class CasernesTest {

	public static List<Firestation> listFirestations;
	public static List<Person> listPersons;
	public static List<Medicalrecord> listMedicalrecords;
	
	@BeforeAll
	public static void setUp() {
		listFirestations = new ArrayList<Firestation>();
		Firestation firestation = new Firestation();
		firestation.setAddress("42, rue des champs");
		firestation.setStation("1");
		listFirestations.add(firestation);
		
		listPersons = new ArrayList<Person>();
		Person person = new Person();
		person.setFirstName("toto");
		person.setLastName("tutu");
		person.setAddress("42, rue des champs");
		listPersons.add(person);
		
		listMedicalrecords = new ArrayList<Medicalrecord>();
		Medicalrecord medicalrecord = new Medicalrecord();
		List<String> medications = new ArrayList<String>();
		List<String> allergies = new ArrayList<String>();
		medications.add("medication");
		allergies.add("allergie");
		medicalrecord.setFirstName("toto");
		medicalrecord.setLastName("tutu");
		medicalrecord.setBirthdate("24/08/2021");
		medicalrecord.setMedications(medications);
		medicalrecord.setAllergies(allergies);
		listMedicalrecords.add(medicalrecord);
	}
	
	@Test
	public void Casernes_ShouldBuildCasernesWithListFirestationPersonMedicalrecord() {
		// GIVEN
		
		
		// WHEN

		// THEN
	}

	@Test
	public void Casernes_ShouldBuildSpecifiedtCasernesWithListFirestationPersonMedicalrecord() {
		// GIVEN

		// WHEN

		// THEN
	}

	@Test
	public void trouverCaserneQuiProtege_ShouldFoundListOfCaserneAddress() throws ParseException {
		// GIVEN
		Firestation firestation = new Firestation();
		firestation.setAddress("42, rue des champs");
		firestation.setStation("2");
		listFirestations.add(firestation);
		Casernes casernes = new Casernes(listFirestations, listPersons, listMedicalrecords);
		
		// WHEN
		List<String> casernesQuiProtege = casernes.trouverCaserneQuiProtege(firestation.getAddress(), listFirestations);

		// THEN
		assertEquals(casernesQuiProtege.get(1), firestation.getStation());
		assertTrue(casernesQuiProtege.size() == 2);
	}

}
