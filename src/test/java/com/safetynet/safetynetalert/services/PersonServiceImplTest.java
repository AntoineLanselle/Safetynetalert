package com.safetynet.safetynetalert.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

	@Mock
	private PersonRepository personRepository;
	@InjectMocks
	private PersonServiceImpl personServiceImpl;

	private static List<Person> listPersons;

	@BeforeEach
	public void init() {
		// j'ajoute deux Person dans ma liste
		listPersons = new ArrayList<Person>();
		Person person1 = new Person();
		Person person2 = new Person();
		person1.setFirstName("toto");
		person1.setLastName("tyty");
		person2.setFirstName("tutu");
		person2.setLastName("titi");
		listPersons.add(person1);
		listPersons.add(person2);
	}

	@Test
	public void getPersons_ShouldReturnListOfPerson() {
		// GIVEN
		when(personRepository.getAllPersons()).thenReturn(listPersons);

		// WHEN

		// THEN
		assertEquals(listPersons, personServiceImpl.getPersons());
	}

	@Test
	public void addPerson_ShouldAddPersonInParamaterInList() throws AlreadyExistException {
		// GIVEN
		Person newPerson = new Person();
		newPerson.setFirstName("FirstName");
		newPerson.setLastName("Lanselle");

		when(personRepository.findByName(newPerson.getFirstName(), newPerson.getLastName())).thenReturn(new Person());
		when(personRepository.addPerson(newPerson)).thenReturn(listPersons.add(newPerson));

		// WHEN
		personServiceImpl.addPerson(newPerson);

		// THEN
		assertTrue(listPersons.contains(newPerson));
	}

	@Test
	public void addPerson_ShouldThrowAlreadyExistException() throws AlreadyExistException {
		// GIVEN
		Person newPerson = new Person();
		newPerson.setFirstName("toto");
		newPerson.setLastName("tyty");

		when(personRepository.findByName(newPerson.getFirstName(), newPerson.getLastName())).thenReturn(newPerson);

		// WHEN

		// THEN
		assertThrows(AlreadyExistException.class, () -> {
			personServiceImpl.addPerson(newPerson);
		});
	}

	@Test
	public void updatePerson_ShouldUpdatePersonInParamaterInList() throws RessourceNotFoundException {
		// GIVEN
		Person updatePerson = new Person();
		updatePerson.setFirstName("toto");
		updatePerson.setLastName("tyty");
		updatePerson.setEmail("newEmail");

		when(personRepository.findByName(updatePerson.getFirstName(), updatePerson.getLastName()))
				.thenReturn(listPersons.get(0));

		// WHEN
		personServiceImpl.updatePerson(updatePerson);

		// THEN
		assertEquals(updatePerson.getEmail(), listPersons.get(0).getEmail());
	}

	@Test
	public void updatePerson_ShouldThrowRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		when(personRepository.findByName(null, null)).thenReturn(new Person());

		// WHEN

		// THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			personServiceImpl.updatePerson(new Person());
		});
	}

	@Test
	public void deletePerson_ShouldDeletePersonInParamaterInList() throws RessourceNotFoundException {
		// GIVEN
		when(personRepository.findByName("toto", "tyty")).thenReturn(listPersons.get(0));

		// WHEN
		personServiceImpl.deletePerson("toto", "tyty");

		// THEN
		verify(personRepository, times(1)).delete(any(Person.class));

	}

	@Test
	public void deletePerson_ShouldThrowRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		when(personRepository.findByName(null, null)).thenReturn(new Person());

		// WHEN

		// THEN
		
		assertThrows(RessourceNotFoundException.class, () -> {
			personServiceImpl.deletePerson(null, null);
		});
		verify(personRepository, times(0)).delete(any(Person.class));
	}

}
