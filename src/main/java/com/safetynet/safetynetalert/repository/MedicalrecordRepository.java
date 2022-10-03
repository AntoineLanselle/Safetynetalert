package com.safetynet.safetynetalert.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;

/**
 * @author Antoine
 *
 */
@Repository
public class MedicalrecordRepository {

	private List<Medicalrecord> listMedicalrecord = new ArrayList<>();

	/**
	 * renvoi le contenu de ce Repository.
	 * 
	 * @return une liste de Medicalrecords.
	 * 
	 */
	public List<Medicalrecord> getAllMedicalrecords() {
		return this.listMedicalrecord;
	}

	/**
	 * ajoute un Medicalrecord au Respository.
	 * 
	 * @param un Medicalrecord que l'on veut ajouter.
	 * 
	 * @return un boolean true si l'action a bien été faite false sinon.
	 * 
	 */
	public boolean addMedicalrecord(Medicalrecord medicalrecord) {
		return this.listMedicalrecord.add(medicalrecord);
	}

	/**
	 * Trouve un Medicalrecord par son nom et prenom dans le Repository.
	 * 
	 * @param un String du prenom du Medicalrecord.
	 * @patam un String du nom du Medicalrecord.
	 * 
	 * @return la Person qui a ce nom et prenom si elle existe, une nouvelle Person
	 *         sinon.
	 * 
	 */
	public Medicalrecord findByName(String firstName, String lastName) {
		for (Medicalrecord medicalrecord : this.listMedicalrecord) {
			if (medicalrecord.getFirstName().equals(firstName) && medicalrecord.getLastName().equals(lastName))
				return medicalrecord;
		}
		return new Medicalrecord();
	}

	/**
	 * Permet de supprimer un Medicalrecord du Repository.
	 * 
	 * @param le Medicalrecord que l'on veut supprimer.
	 * 
	 */
	public void delete(Medicalrecord medicalrecord) {
		this.listMedicalrecord.remove(medicalrecord);
	}

}
