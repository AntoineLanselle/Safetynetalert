package com.safetynet.safetynetalert.entities.modele2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.entities.modele1.Person;

public class PersonneTest {

	static List<Medicalrecord> listMedicalrecords;
	static Person person;

	@BeforeAll
	public static void setUp() {
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

		person = new Person();
		person.setFirstName("toto");
		person.setLastName("tutu");
	}

	@Test
	public void Personne_ShouldBuildPersonneWithPersonAndMedicalrecordsList() throws ParseException {
		// GIVEN

		// WHEN
		Personne personne = new Personne(person, listMedicalrecords);

		// THEN
		assertEquals(personne.getClass(), Personne.class);
		assertEquals(personne.getFirstName(), person.getFirstName());
		assertEquals(personne.getLastName(), person.getLastName());
		assertTrue(personne.getAge() >= 0);
		assertEquals(personne.getDossierMedical().getAllergies(), listMedicalrecords.get(0).getAllergies());
		assertEquals(personne.getDossierMedical().getMedications(), listMedicalrecords.get(0).getMedications());

	}

	@Test
	public void Personne_ShouldBuildPersonneWithPerson() throws ParseException {
		// GIVEN

		// WHEN
		Personne personne = new Personne(person);

		// THEN
		assertEquals(personne.getClass(), Personne.class);
		assertEquals(personne.getFirstName(), person.getFirstName());
		assertEquals(personne.getLastName(), person.getLastName());
	}

	@Test
	public void findMedicalrecord_ShouldFindSpecificMedicalrecordInList() throws ParseException {
		// GIVEN
		Personne personne = new Personne(person);

		// WHEN
		Medicalrecord medicalrecord = personne.findMedicalrecord(listMedicalrecords);

		// THEN
		assertEquals(medicalrecord.getFirstName(), personne.getFirstName());
		assertEquals(medicalrecord.getLastName(), personne.getLastName());
	}

	@Test
	public void findMedicalrecord_ShouldNotFindSpecificMedicalrecordInList() throws ParseException {
		// GIVEN
		Personne personne = new Personne(person);
		personne.setFirstName("notFound");
		personne.setLastName("notFound");

		// WHEN
		Medicalrecord medicalrecord = personne.findMedicalrecord(listMedicalrecords);

		// THEN
		assertEquals(medicalrecord, null);
	}
	
	@Test //TODO
	public void calculeAge_ShouldCalculateAgeFromDateString() {
		// GIVEN

		// WHEN
		
		// THEN
		
	}

}
