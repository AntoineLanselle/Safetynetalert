package com.safetynet.safetynetalert.entities.modele2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;

public class DossierMedicalTest {

	@Test
	public void DossierMedical_ShouldBuildDossierMedical() {
		// GIVEN
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
		
		// WHEN
		DossierMedical dossierMedical = new DossierMedical(medicalrecord);

		// THEN
		assertEquals(dossierMedical.getClass(), DossierMedical.class);
		assertEquals(dossierMedical.getAllergies().get(0), allergies.get(0));
		assertEquals(dossierMedical.getMedications().get(0), medications.get(0));
	}

}
