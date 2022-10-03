package com.safetynet.safetynetalert.entities.modele2;

import java.util.List;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Antoine
 *
 */
@Getter
@Setter
public class DossierMedical {

	public List<String> medications;
	public List<String> allergies;

	/**
	 * Construit un dossier medical à partir des données medical passées en
	 * parametre.
	 * 
	 * @param les données medicales du dossier.
	 * 
	 */
	public DossierMedical(Medicalrecord medicalrecord) {
		this.setMedications(medicalrecord.getMedications());
		this.setAllergies(medicalrecord.getAllergies());
	}

}
