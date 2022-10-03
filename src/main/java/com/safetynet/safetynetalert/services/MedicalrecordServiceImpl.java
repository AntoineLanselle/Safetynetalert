package com.safetynet.safetynetalert.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.repository.MedicalrecordRepository;

/**
 * @author Antoine
 *
 */
@Service
public class MedicalrecordServiceImpl implements MedicalrecordService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MedicalrecordServiceImpl.class);
	
	@Autowired
	private MedicalrecordRepository medicalrecordRepository;

	/**
	 * Recupere toutes les données des Medicalrecords du fichier Json.
	 *
	 * @return une liste de toutes les Medicalrecords du fichier Json.
	 * 
	 */
	@Override
	public List<Medicalrecord> getMedicalrecords() {
		return medicalrecordRepository.getAllMedicalrecords();
	}

	/**
	 * Ajouter un dossier médical.
	 * 
	 * @param Le dossier medical que l'on veut ajouter.
	 * 
	 * @throws AlreadyExistException 
	 * 
	 */
	@Override
	public void addMedicalrecord(Medicalrecord medicalrecord) throws AlreadyExistException {
		Medicalrecord newMedicalrecord = new Medicalrecord();
		newMedicalrecord = this.medicalrecordRepository.findByName(medicalrecord.getFirstName(), medicalrecord.getLastName());

		if (newMedicalrecord.getFirstName()!=null) {
			String error = String.format("Medical Records %s %s already exist", medicalrecord.getFirstName(), medicalrecord.getLastName());
			LOGGER.error(error);
			throw new AlreadyExistException(error);
		}
		this.medicalrecordRepository.addMedicalrecord(medicalrecord);
	}

	/**
	 * Mettre à jour un dossier médical existant.
	 * 
	 * @param le nouveau dossier medical.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@Override
	public void updateMedicalrecord(Medicalrecord medicalrecord) throws RessourceNotFoundException {
		Medicalrecord newMedicalrecord = new Medicalrecord();
		newMedicalrecord = this.medicalrecordRepository.findByName(medicalrecord.getFirstName(), medicalrecord.getLastName());

		if (newMedicalrecord.getFirstName()==null) {
			String error = String.format("Medical Records %s %s not found", medicalrecord.getFirstName(), medicalrecord.getLastName());
			LOGGER.error(error);
			throw new RessourceNotFoundException(error);
		}
		newMedicalrecord.setFirstName(medicalrecord.getFirstName());
		newMedicalrecord.setLastName(medicalrecord.getLastName());
		newMedicalrecord.setBirthdate(medicalrecord.getBirthdate());
		newMedicalrecord.setAllergies(medicalrecord.getAllergies());
		newMedicalrecord.setMedications(medicalrecord.getMedications());
	}

	/**
	 * Supprimer un dossier médical.
	 * 
	 * @param le prenom de la personne à la quelle est rataché le dossier medical.
	 * @param le nom de la personne à la quelle est rataché le dossier medical.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@Override
	public void deleteMedicalrecord(String firstName, String lastName) throws RessourceNotFoundException {
		Medicalrecord newMedicalrecord = new Medicalrecord();
		newMedicalrecord = this.medicalrecordRepository.findByName(firstName, lastName);
		
		if (newMedicalrecord.getFirstName()==null) {
			String error = String.format("Medical Records %s %s not found", firstName, lastName);
			LOGGER.error(error);
			throw new RessourceNotFoundException(error);
		}
		this.medicalrecordRepository.delete(newMedicalrecord);
	}

}
