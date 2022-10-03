package com.safetynet.safetynetalert.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.repository.PersonRepository;

/**
 * @author Antoine
 *
 */
@Service
public class PersonServiceImpl implements PersonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonRepository personRepository;

	/**
	 * Recupere toutes les données des Persons du fichier Json.
	 *
	 * @return une liste de toutes les Persons du fichier Json.
	 * 
	 */
	@Override
	public List<Person> getPersons() {
		return personRepository.getAllPersons();
	}

	/**
	 * Ajouter une nouvelle personne.
	 * 
	 * @param la personne à ajouter.
	 * 
	 * @throws AlreadyExistException 
	 * 
	 */
	@Override
	public void addPerson(Person person) throws AlreadyExistException {
		Person newPerson = new Person();
		newPerson = this.personRepository.findByName(person.getFirstName(), person.getLastName());
		
		if (newPerson.getFirstName()!=null) {
			String error = String.format("%s %s already exist", person.getFirstName(), person.getLastName());
			LOGGER.error(error);
			throw new AlreadyExistException(error);
		}
		this.personRepository.addPerson(person);
	}

	/**
	 * Mettre à jour une personne existante.
	 * 
	 * @param Les nouvelles informations de la personne.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@Override
	public void updatePerson(Person person) throws RessourceNotFoundException {
		Person newPerson = new Person();
		newPerson = this.personRepository.findByName(person.getFirstName(), person.getLastName());
		
		if (newPerson.getFirstName()==null) {
			String error = String.format("%s %s not found", person.getFirstName(), person.getLastName());
			LOGGER.error(error);
			throw new RessourceNotFoundException(error);
		}
		newPerson.setFirstName(person.getFirstName());
		newPerson.setLastName(person.getLastName());
		newPerson.setAddress(person.getAddress());
		newPerson.setCity(person.getCity());
		newPerson.setZip(person.getZip());
		newPerson.setPhone(person.getPhone());
		newPerson.setEmail(person.getEmail());	
	}

	/**
	 * Supprimer une personne.
	 * 
	 * @param Le prenom de la personne à supprimer.
	 * @param Le nom de la personne à supprimer.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@Override
	public void deletePerson(String firstName, String lastName) throws RessourceNotFoundException {
		Person newPerson = new Person();
		newPerson = this.personRepository.findByName(firstName, lastName);

		if (newPerson.getFirstName()==null) {
			String error = String.format("%s %s not found", firstName, lastName);
			LOGGER.error(error);
			throw new RessourceNotFoundException(error);
		}
		this.personRepository.delete(newPerson);
	}

}
