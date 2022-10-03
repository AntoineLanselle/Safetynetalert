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

public class FamilleTest {

	static List<Medicalrecord> listMedicalrecords;

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
	}

	@Test
	public void Famille_ShouldBuildFamilleWithAddress() {
		// GIVEN
		String address = "42, rue des champs";

		// WHEN
		Famille famille = new Famille(address);

		// THEN
		assertEquals(famille.getAddress(), address);
		assertEquals(famille.getMembres(), new ArrayList<Personne>());
	}

	@Test
	public void ajouterMembre_ShouldAddMemberInFamilleAndIncrementCounter() throws ParseException {
		// GIVEN
		Famille famille = new Famille("42, rue des champs");
		Person person = new Person();
		person.setFirstName("toto");
		person.setLastName("tutu");

		Personne personne = new Personne(person);
		// WHEN
		famille.ajouterMembre(person, listMedicalrecords);

		// THEN
		assertEquals(famille.getMembres().get(0).getFirstName(), personne.getFirstName());
		assertEquals(famille.getMembres().get(0).getLastName(), personne.getLastName());
		assertTrue(famille.getNombreEnfant() > 0 || famille.getNombreAdulte() > 0);
	}

	@Test
	public void trouverMembres_ShouldAddAllMembersOfFamilleFromPersonList() throws ParseException {
		// GIVEN
		Famille famille = new Famille("42, rue des champs");
		List<Person> listPersons = new ArrayList<Person>();
		Person person = new Person();
		person.setFirstName("toto");
		person.setLastName("tutu");
		person.setAddress("42, rue des champs");
		listPersons.add(person);

		// WHEN
		famille.trouverMembres(listPersons, listMedicalrecords);

		// THEN
		assertEquals(listPersons.get(0).getFirstName(), famille.getMembres().get(0).getFirstName());
		assertEquals(listPersons.get(0).getLastName(), famille.getMembres().get(0).getLastName());
		assertTrue(famille.getMembres().size() == 1);
	}

	@Test //TODO
	public void equals_ShouldReturnIfOjectisEqual() {
		// GIVEN

		// WHEN

		// THEN
	}

}
