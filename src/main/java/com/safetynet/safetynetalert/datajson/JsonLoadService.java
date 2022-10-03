package com.safetynet.safetynetalert.datajson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.repository.FirestationRepository;
import com.safetynet.safetynetalert.repository.MedicalrecordRepository;
import com.safetynet.safetynetalert.repository.PersonRepository;

/**
 * @author Antoine
 *
 */
@Service
public class JsonLoadService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FirestationRepository firestationRepository;
	@Autowired
	private MedicalrecordRepository medicalrecordRepository;

	@Value("${dataSourceJson}")
	private String dataFilePath;

	/**
	 * 
	 * 
	 */
	@PostConstruct
	public void readJsonData() {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root;
		try {
			root = mapper.readTree(new FileInputStream(dataFilePath));
			loadPersonData(root);
			loadFirestationData(root);
			loadMedicalrecordData(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Créé toutes les Persons du fichier Json dans le Repository de Person.
	 * 
	 * @param le fichier Json mappé.
	 * 
	 */
	public void loadPersonData(JsonNode root) {

		JsonNode persons = root.path("persons");

		for (JsonNode nodePerson : persons) {

			Person person = new Person();
			person.setFirstName(nodePerson.path("firstName").asText());
			person.setLastName(nodePerson.path("lastName").asText());
			person.setAddress(nodePerson.path("address").asText());
			person.setCity(nodePerson.path("city").asText());
			person.setZip(nodePerson.path("zip").asText());
			person.setPhone(nodePerson.path("phone").asText());
			person.setEmail(nodePerson.path("email").asText());

			personRepository.addPerson(person);
		}

	}

	/**
	 * Créé toutes les Firestations du fichier Json dans le Repository de
	 * Firestation.
	 * 
	 * @param le fichier Json mappé.
	 * 
	 */
	public void loadFirestationData(JsonNode root) {

		JsonNode firestations = root.path("firestations");

		for (JsonNode nodeFirestation : firestations) {

			Firestation firestation = new Firestation();
			firestation.setAddress(nodeFirestation.path("address").asText());
			firestation.setStation(nodeFirestation.path("station").asText());

			firestationRepository.addFirestation(firestation);
		}

	}

	/**
	 * créé toutes les Medicalrecord du fichier Json dans le Repository de
	 * Medicalrecord.
	 * 
	 * @param le fichier Json mappé.
	 * 
	 */
	public void loadMedicalrecordData(JsonNode root) {

		JsonNode medicalrecords = root.path("medicalrecords");

		for (JsonNode nodeMedicalrecord : medicalrecords) {
			List<String> medications = new ArrayList<>();
			List<String> allergies = new ArrayList<>();
			Medicalrecord medicalrecord = new Medicalrecord();
			medicalrecord.setFirstName(nodeMedicalrecord.path("firstName").asText());
			medicalrecord.setLastName(nodeMedicalrecord.path("lastName").asText());
			medicalrecord.setBirthdate(nodeMedicalrecord.path("birthdate").asText());

			for (JsonNode nodeMedications : nodeMedicalrecord.path("medications")) {
				medications.add(nodeMedications.asText());

			}
			medicalrecord.setMedications(medications);

			for (JsonNode nodeAllergies : nodeMedicalrecord.path("allergies")) {
				allergies.add(nodeAllergies.asText());
			}
			medicalrecord.setAllergies(allergies);

			medicalrecordRepository.addMedicalrecord(medicalrecord);
		}

	}

}
