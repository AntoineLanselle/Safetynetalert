package com.safetynet.safetynetalert.controller;

import java.util.List;

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

import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.services.PersonService;

/**
 * @author Antoine
 *
 */
@RestController
@RequestMapping(path = "/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	/**
	 * Renvoie la liste complete de toutes les personnes.
	 * 
	 * @return response entity statu OK avec comme body une liste de toutes les
	 *         persons.
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<Person>> getAllPersons() {
		return ResponseEntity.status(HttpStatus.OK).body(this.personService.getPersons());
	}

	/**
	 * Ajouter une nouvelle person.
	 * 
	 * @param la person à ajouter.
	 * 
	 * @return response entity statu OK avec comme body un message pour signaler ce
	 *         qui a ete fait.
	 *         
	 * @throws AlreadyExistException
	 * 
	 */
	@PostMapping()
	public ResponseEntity<String> addPerson(@RequestBody Person person) throws AlreadyExistException {
		this.personService.addPerson(person);
		return ResponseEntity.status(HttpStatus.OK)
				.body("La personne " + person.getFirstName() + " " + person.getLastName() + " a bien été créée");
	}

	/**
	 * Mettre à jour une person existante.
	 * 
	 * @param Les nouvelles informations de la person.
	 * 
	 * @return response entity statu OK avec comme body un message pour signaler ce
	 *         qui a ete fait.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@PutMapping()
	public ResponseEntity<String> updatePerson(@RequestBody Person person) throws RessourceNotFoundException {
		this.personService.updatePerson(person);
		return ResponseEntity.status(HttpStatus.OK)
				.body("La personne " + person.getFirstName() + " " + person.getLastName() + " a bien été modifiée");
	}

	/**
	 * Supprimer une person.
	 * 
	 * @param Le prenom de la person à supprimer.
	 * @param Le nom de la person à supprimer.
	 * 
	 * @return response entity statu OK avec comme body un message pour signaler ce
	 *         qui a ete fait.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deletePerson(@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName", required = true) String lastName) throws RessourceNotFoundException {
		this.personService.deletePerson(firstName, lastName);
		return ResponseEntity.status(HttpStatus.OK)
				.body("La personne " + firstName + " " + lastName + " a bien été suprimée");
	}

}
