package com.safetynet.safetynetalert.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.services.MedicalrecordService;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;

/**
 * @author Antoine
 *
 */
@RestController
@RequestMapping(path = "/medicalrecords")
public class MedicalrecordController {
	
	private static final Logger LOGGER = LogManager.getLogger(MedicalrecordController.class);

	@Autowired
	private MedicalrecordService medicalrecordService;

	/**
	 * Renvoie la liste complete des données medicales.
	 * 
	 * @return response entity statu OK avec comme body une liste de toutes les
	 *         données medicales.
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<Medicalrecord>> getAllMedicalrecords() {
		LOGGER.info("received Get request - http://localhost:8080/medicalrecords");
		return ResponseEntity.status(HttpStatus.OK).body(this.medicalrecordService.getMedicalrecords());
	}

	/**
	 * Ajouter un dossier médical.
	 * 
	 * @param Le dossier medical que l'on veut ajouter.
	 * 
	 * @return response entity statu OK avec comme body un message pour signaler ce
	 *         qui a ete fait.
	 *         
	 * @throws AlreadyExistException 
	 * 
	 */
	@PostMapping()
	public ResponseEntity<String> addMedicalrecord(@RequestBody Medicalrecord medicalrecord) throws AlreadyExistException {
		LOGGER.info("received Post request - http://localhost:8080/medicalrecords with body"+medicalrecord);
		this.medicalrecordService.addMedicalrecord(medicalrecord);
		return ResponseEntity.status(HttpStatus.OK).body("Le dossier medical de " + medicalrecord.getFirstName() + " "
				+ medicalrecord.getLastName() + " a bien été créé");
	}

	/**
	 * Mettre à jour un dossier médical existant.
	 * 
	 * @param le nouveau dossier medical.
	 * 
	 * @return response entity statu OK avec comme body un message pour signaler ce
	 *         qui a ete fait.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@PutMapping()
	public ResponseEntity<String> updateMedicalrecord(@RequestBody Medicalrecord medicalrecord)
			throws RessourceNotFoundException {
		LOGGER.info("received Put request - http://localhost:8080/medicalrecords"+ medicalrecord);
		this.medicalrecordService.updateMedicalrecord(medicalrecord);
		return ResponseEntity.status(HttpStatus.OK).body("Le dossier medical de " + medicalrecord.getFirstName() + " "
				+ medicalrecord.getLastName() + " a bien été modifié");
	}

	/**
	 * Supprimer un dossier médical.
	 * 
	 * @param le prenom de la personne à la quelle est rataché le dossier medical.
	 * @param le nom de la personne à la quelle est rataché le dossier medical.
	 * 
	 * @return response entity OK avec comme body un message pour signaler ce qui a
	 *         ete fait.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteMedicalrecord(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName", required = true) String lastName) throws RessourceNotFoundException {
		LOGGER.info("received Delete request - http://localhost:8080/medicalrecords?firstName="+firstName+"&lastName="+lastName);
		this.medicalrecordService.deleteMedicalrecord(firstName, lastName);
		return ResponseEntity.status(HttpStatus.OK)
				.body("Le dossier medical de " + firstName + " " + lastName + " a bien été supprimé");
	}

}
