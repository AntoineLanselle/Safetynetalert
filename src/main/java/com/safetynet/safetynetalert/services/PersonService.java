package com.safetynet.safetynetalert.services;

import java.util.List;

import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;

/**
 * @author Antoine
 *
 */
public interface PersonService {

	public List<Person> getPersons();

	public void addPerson(Person person) throws AlreadyExistException;

	public void updatePerson(Person person) throws RessourceNotFoundException;

	public void deletePerson(String firstName, String lastName) throws RessourceNotFoundException;

}
