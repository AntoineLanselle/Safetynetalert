package com.safetynet.safetynetalert.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.safetynetalert.entities.modele1.Person;

/**
 * @author Antoine
 *
 */
@Repository
public class PersonRepository {

	private List<Person> listPerson = new ArrayList<>();

	/**
	 * renvoi le contenu de ce Repository.
	 * 
	 * @return une liste de Persons.
	 * 
	 */
	public List<Person> getAllPersons() {
		return this.listPerson;
	}

	/**
	 * ajoute une Person au Respository.
	 * 
	 * @param une Person que l'on veut ajouter.
	 * 
	 * @return un boolean true si l'action a bien été faite false sinon.
	 * 
	 */
	public boolean addPerson(Person person) {
		return this.listPerson.add(person);
	}

	/**
	 * Trouve une Person par son nom et prenom dans le Repository.
	 * 
	 * @param un String du prenom de la Person.
	 * @patam un String du nom de la Person.
	 * 
	 * @return la Person qui a ce nom et prenom si elle existe, une nouvelle Person
	 *         sinon.
	 * 
	 */
	public Person findByName(String firstName, String lastName) {
		for (Person person : this.listPerson) {
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
				return person;
		}
		return new Person();
	}

	/**
	 * Permet de supprimer une Person du Repository.
	 * 
	 * @param la Person que l'on veut supprimer.
	 * 
	 */
	public void delete(Person person) {
		this.listPerson.remove(person);
	}

}
