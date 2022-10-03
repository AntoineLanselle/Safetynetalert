package com.safetynet.safetynetalert.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.entities.modele2.Casernes;
import com.safetynet.safetynetalert.entities.modele2.Famille;
import com.safetynet.safetynetalert.entities.modele2.Personne;
import com.safetynet.safetynetalert.entities.response.ResponseChildAlert;
import com.safetynet.safetynetalert.entities.response.ResponseFire;

/**
 * @author Antoine
 *
 */
@Service
public class UrlsServiceImpl implements UrlsService {

	@Autowired
	private FirestationService firestationService;
	@Autowired
	private PersonService personService;
	@Autowired
	private MedicalrecordService medicalrecordService;

	/**
	 * Retourne une liste d'enfants habitant à cette adresse.
	 * 
	 * @param L'adresse de la famille.
	 * 
	 * @return Un objet ResponseChildAlert qui contient une liste des prénoms et des
	 *         noms de famille de chaque enfant, leurs âges et une liste des autres
	 *         membres du foyer. Une chaîne vide s'il n'y a pas d'enfant.
	 * 
	 */
	@Override
	public ResponseChildAlert childAlert(String address) throws IOException, ParseException {

		List<Person> personList = personService.getPersons();
		List<Medicalrecord> medicalrecordList = medicalrecordService.getMedicalrecords();

		ResponseChildAlert response = new ResponseChildAlert();
		Famille famille = new Famille(address);
		famille.trouverMembres(personList, medicalrecordList);

		for (Personne personne : famille.getMembres()) {
			if (personne.getAge() <= 18) {
				response.getEnfants().add(personne);
			} else {
				response.getAutresMembres().add(personne);
			}
		}

		return response;
	}

	/**
	 * Retourne une liste des numéros de téléphone des résidents desservis par la
	 * caserne de pompiers.
	 * 
	 * @param Le numero de la caserne de pompier concernée.
	 * 
	 * @return Une liste de String qui contient les numeros de telephones.
	 * 
	 */
	@Override
	public List<String> phoneAlert(String firestation_number) throws IOException, ParseException {

		List<Firestation> firestationList = firestationService.getFirestations();
		List<Person> personList = personService.getPersons();
		List<Medicalrecord> medicalrecordList = medicalrecordService.getMedicalrecords();

		Casernes caserne = new Casernes(firestation_number, firestationList, personList, medicalrecordList);
		List<String> listPhoneNumber = new ArrayList<String>();

		for (Famille famille : caserne.getCasernes().get(firestation_number)) {
			for (Personne personne : famille.getMembres()) {
				listPhoneNumber.add(personne.getPhone());
			}
		}

		return listPhoneNumber.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * Retourne la liste des habitants vivant à l’adresse donnée ainsi que le numéro
	 * de la caserne de pompiers la desservant.
	 * 
	 * @param L'adresse de la famille.
	 * 
	 * @return Un objet ResponseFire qui contient une liste des noms, des numeros de
	 *         telephones, les ages ainsi que les antecedents medicaux des
	 *         habitants, et, une liste des casernes de pompiers la desservant.
	 * 
	 */
	@Override
	public ResponseFire fire(String address) throws IOException, ParseException {

		// j initialise mes variables
		List<Firestation> firestationList = firestationService.getFirestations();
		List<Person> personList = personService.getPersons();
		List<Medicalrecord> medicalrecordList = medicalrecordService.getMedicalrecords();

		Casernes casernes = new Casernes(firestationList, personList, medicalrecordList);
		ResponseFire response = new ResponseFire();

		// je cherche l address en parametre
		List<String> casernesQuiProtege = new ArrayList<String>();

		for (Entry<String, List<Famille>> caserne : casernes.getCasernes().entrySet()) {
			for (Famille famille : caserne.getValue()) {
				if (famille.getAddress().equals(address)) {
					casernesQuiProtege.add(caserne.getKey());
					if (response.getPersonnesVivantAddress().size() == 0) {
						response.setPersonnesVivantAddress(famille.getMembres());
					}
				}
			}
		}

		response.setCasernesDeservantAdress(casernesQuiProtege);

		// retourner la valeur
		return response;
	}


	/**
	 * Retourne une liste de tous les foyers desservis par les casernes.
	 * 
	 * @param La liste des casernes.
	 * 
	 * @return Une liste de Familles desservient par les stations qui fait
	 *         apparaitre le nom, le numero de telephone, l age et les antecedents
	 *         medicaux de chaque membre.
	 * 
	 */
	@Override
	public List<Famille> floodStations(List<String> station_numbers) throws IOException, ParseException {

		List<Firestation> firestationList = firestationService.getFirestations();
		List<Person> personList = personService.getPersons();
		List<Medicalrecord> medicalrecordList = medicalrecordService.getMedicalrecords();

		Casernes casernes = new Casernes(firestationList, personList, medicalrecordList);
		List<Famille> listeFamillesCouvertes = new ArrayList<Famille>();

		for (String station : station_numbers) {
			for (Entry<String, List<Famille>> caserne : casernes.getCasernes().entrySet()) {
				if (caserne.getKey().equals(station)) {
					for (Famille famille : caserne.getValue()) {
						listeFamillesCouvertes.add(famille);
					}
				}
			}
		}

		return listeFamillesCouvertes;
	}

	/**
	 * Retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents
	 * médicaux des habitants qui portent ce nom, prenom.
	 * 
	 * @param Le prenom de la personne.
	 * @param Le nom de la personne.
	 * 
	 * @return Une liste des personnes qui portent ce nom, prenom, et qui fait
	 *         apparaitre leurs informations.
	 * 
	 */
	@Override
	public List<Personne> personInfo(String firstName, String lastName) throws IOException, ParseException {

		List<Person> personList = personService.getPersons();
		List<Medicalrecord> medicalrecordList = medicalrecordService.getMedicalrecords();

		List<Personne> listeDePersonnesPortantCeNom = new ArrayList<Personne>();

		for (Person person : personList) {
			if (person.getLastName().equals(lastName)) {
				Personne personne = new Personne(person, medicalrecordList);
				listeDePersonnesPortantCeNom.add(personne);
			}
		}

		return listeDePersonnesPortantCeNom;
	}

	/**
	 * Retourne les adresses mail de tous les habitants de la ville.
	 * 
	 * @param Le nom de la ville.
	 * 
	 * @return Une list de String contenant tous les emails des habitants.
	 * 
	 */
	@Override
	public List<String> communityEmail(String city) throws IOException {

		List<Person> personList = personService.getPersons();
		List<String> emailsHabitants = new ArrayList<String>();

		for (Person person : personList) {
			if (person.getCity().equals(city)) {
				emailsHabitants.add(person.getEmail());
			}
		}

		return emailsHabitants.stream().distinct().collect(Collectors.toList());
	}

}
