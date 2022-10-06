package com.safetynet.safetynetalert.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalert.entities.modele2.Famille;
import com.safetynet.safetynetalert.entities.modele2.Personne;
import com.safetynet.safetynetalert.entities.response.ResponseChildAlert;
import com.safetynet.safetynetalert.entities.response.ResponseFire;
import com.safetynet.safetynetalert.services.UrlsService;

/**
 * @author Antoine
 *
 */
@RestController
public class UrlsController {

	private static final Logger LOGGER = LogManager.getLogger(UrlsController.class);

	@Autowired
	private UrlsService urlsService;

	/**
	 * Retourne une liste d'enfants habitant à cette adresse.
	 * 
	 * @param L'adresse de la famille.
	 * 
	 * @return response entity statu OK avec comme body un objet ResponseChildAlert.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	@GetMapping(path = "/childAlert")
	public ResponseEntity<MappingJacksonValue> childAlert(
			@RequestParam(name = "address", required = true) String address) throws IOException, ParseException {
		LOGGER.info("received Get request - http://localhost:8080/childAlert?address="+address);

		ResponseChildAlert response = this.urlsService.childAlert(address);

		SimpleBeanPropertyFilter enfantsFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName",
				"age");

		FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonne",
				enfantsFiltre);

		MappingJacksonValue filtres = new MappingJacksonValue(response);

		filtres.setFilters(listDeNosFiltres);

		return ResponseEntity.status(HttpStatus.OK).body(filtres);
	}

	/**
	 * Retourne une liste des numéros de téléphone des résidents desservis par la
	 * caserne de pompiers.
	 * 
	 * @param Le numero de la caserne de pompier concernée.
	 * 
	 * @return response entity statu OK avec comme body une liste de String qui
	 *         contient les numeros de telephones.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	@GetMapping(path = "/phoneAlert")
	public ResponseEntity<MappingJacksonValue> phoneAlert(
			@RequestParam(name = "firestation", required = true) String firestation_number)
			throws IOException, ParseException {
		LOGGER.info("received Get request - http://localhost:8080/phoneAlert?firestation="+firestation_number);
		List<String> response = this.urlsService.phoneAlert(firestation_number);
		MappingJacksonValue filtres = new MappingJacksonValue(response);
		return ResponseEntity.status(HttpStatus.OK).body(filtres);
	}

	/**
	 * Retourne la liste des habitants vivant à l’adresse donnée ainsi que le numéro
	 * de la caserne de pompiers la desservant.
	 * 
	 * @param L'adresse de la famille.
	 * 
	 * @return response entity statu OK avec comme body un objet ResponseFire qui
	 *         contient une liste des noms, des numeros de telephones, les ages
	 *         ainsi que les antecedents medicaux des habitants, et, une liste des
	 *         casernes de pompiers la desservant.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	@GetMapping(path = "/fire")
	public ResponseEntity<MappingJacksonValue> fire(@RequestParam(name = "address", required = true) String address)
			throws IOException, ParseException {
		LOGGER.info("received Get request - http://localhost:8080/fire?address="+address);

		ResponseFire response = this.urlsService.fire(address);

		SimpleBeanPropertyFilter fireFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "phone", "age",
				"dossierMedical");

		FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonne", fireFiltre);

		MappingJacksonValue filtres = new MappingJacksonValue(response);

		filtres.setFilters(listDeNosFiltres);

		return ResponseEntity.status(HttpStatus.OK).body(filtres);
	}

	/**
	 * Retourne une liste de tous les foyers desservis par les casernes.
	 * 
	 * @param La liste des casernes.
	 * 
	 * @return response entity statu OK avec comme body une liste de Familles
	 *         desservis par les stations qui fait apparaitre le nom, le numero de
	 *         telephone, l age et les antecedents medicaux de chaque membre.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	@GetMapping(path = "/flood/stations")
	public ResponseEntity<MappingJacksonValue> floodStations(
			@RequestParam(name = "stations", required = true) List<String> station_numbers)
			throws IOException, ParseException {
		LOGGER.info("received Get request - http://localhost:8080/flood/stations?stations="+station_numbers);

		List<Famille> response = this.urlsService.floodStations(station_numbers);

		SimpleBeanPropertyFilter floodStationsFiltrePersonne = SimpleBeanPropertyFilter.filterOutAllExcept("lastName",
				"phone", "age", "dossierMedical");
		SimpleBeanPropertyFilter floodStationsFiltreFamille = SimpleBeanPropertyFilter.filterOutAllExcept("address",
				"membres");

		FilterProvider listDeNosFiltres = new SimpleFilterProvider()
				.addFilter("filtreDynamiquePersonne", floodStationsFiltrePersonne)
				.addFilter("filtreDynamiqueFamille", floodStationsFiltreFamille);

		MappingJacksonValue filtres = new MappingJacksonValue(response);

		filtres.setFilters(listDeNosFiltres);

		return ResponseEntity.status(HttpStatus.OK).body(filtres);
	}

	/**
	 * Retourne le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux
	 * des habitants qui portent ce nom, prenom.
	 * 
	 * @param Le prenom de la personne.
	 * @param Le nom de la personne.
	 * 
	 * @return response entity statu OK avec comme body une liste des personnes qui
	 *         portent ce nom, prenom, et qui fait apparaitre leurs informations.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	@GetMapping(path = "/personInfo")
	public ResponseEntity<MappingJacksonValue> personInfo(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName", required = true) String lastName) throws IOException, ParseException {
		LOGGER.info("received Get request - http://localhost:8080/personInfo?firstName="+firstName+"&lastName="+lastName);

		List<Personne> response = this.urlsService.personInfo(firstName, lastName);

		SimpleBeanPropertyFilter personInfoFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "address",
				"age", "email", "dossierMedical");

		FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonne",
				personInfoFiltre);

		MappingJacksonValue filtres = new MappingJacksonValue(response);

		filtres.setFilters(listDeNosFiltres);

		return ResponseEntity.status(HttpStatus.OK).body(filtres);
	}

	/**
	 * Retourne les adresses mail de tous les habitants de la ville.
	 * 
	 * @param Le nom de la ville.
	 * 
	 * @return response entity statu OK avec comme body une liste de String
	 *         contenant tous les emails des habitants.
	 * 
	 * @throws IOException
	 * 
	 */
	@GetMapping(path = "/communityEmail")
	public ResponseEntity<MappingJacksonValue> communityEmail(@RequestParam(name = "city", required = true) String city)
			throws IOException {
		LOGGER.info("received Get request - http://localhost:8080/communityEmail?city="+city);
		List<String> response = this.urlsService.communityEmail(city);
		MappingJacksonValue filtres = new MappingJacksonValue(response);
		return ResponseEntity.status(HttpStatus.OK).body(filtres);

	}

}
