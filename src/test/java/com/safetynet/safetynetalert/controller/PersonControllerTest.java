package com.safetynet.safetynetalert.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.services.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;
	
	@Test
	public void getAllPerson_ShouldReturnListOfPersonWithStatusOK() {
		// GIVEN
		List<Person> listPerson = new ArrayList<Person>();
		Person person = new Person();
		person.setFirstName("toto");
		person.setLastName("tutu");
		listPerson.add(person);
		when(personService.getPersons()).thenReturn(listPerson);
		
		// WHEN
		ResponseEntity<List<Person>> result = personController.getAllPersons();

		// THEN
		assertEquals(result.getBody().get(0).getClass(), person.getClass());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	} 

	@Test
	public void addPerson_ShouldAddPersonWithStatusOK() throws AlreadyExistException {
		// GIVEN
		Person person = new Person();
		person.setFirstName("toto");
		person.setLastName("tutu");
		
		// WHEN
		ResponseEntity<String> result = personController.addPerson(person);
		
		// THEN
		assertEquals(result.getBody(), "La personne " + person.getFirstName() + " " + person.getLastName() + " a bien été créée");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void updatePerson_ShouldUpdatePersonWithStatusOK() throws RessourceNotFoundException {
		// GIVEN
		Person person = new Person();
		person.setFirstName("toto");
		person.setLastName("tutu");
		
		// WHEN
		ResponseEntity<String> result = personController.updatePerson(person);
		
		// THEN
		assertEquals(result.getBody(), "La personne " + person.getFirstName() + " " + person.getLastName() + " a bien été modifiée");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	} 
	
	@Test
	public void deletePerson_ShouldDeletePersonWithStatusOK() throws RessourceNotFoundException {
		// GIVEN
		Person person = new Person();
		person.setFirstName("toto");
		person.setLastName("tutu");
		
		// WHEN
		ResponseEntity<String> result = personController.deletePerson(person.getFirstName(), person.getLastName());
		
		// THEN
		assertEquals(result.getBody(), "La personne " + person.getFirstName() + " " + person.getLastName() + " a bien été suprimée");
		assertEquals(HttpStatus.OK, result.getStatusCode());
	} 

}
