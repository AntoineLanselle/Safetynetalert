package com.safetynet.safetynetalert.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.response.ResponseGetFirestation;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.services.FirestationService;

/**
 * @author Antoine
 *
 */
@RestController
@RequestMapping(path = "/firestation")
public class FirestationController {

	@Autowired
	private FirestationService firestationService;

	/**
	 * Renvoie la liste complete des données firestations.
	 * 
	 * @return response entity statu OK avec comme body une liste de toutes les
	 *         firestations.
	 * 
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<List<Firestation>> getAllFirestations() {
		return ResponseEntity.status(HttpStatus.OK).body(this.firestationService.getFirestations());
	}

	/**
	 * Retourne une liste des personnes couvertes par la caserne de pompiers
	 * correspondante.
	 * 
	 * @param le numero de la caserne sous forme d'un String.
	 * 
	 * @return response entity statu OK avec comme body un objet
	 *         ResponseGetFirestation qui contient une liste des personnes couvertes
	 *         ainsi que le nombre d'enfant et d'adultes.
	 * 
	 * @throws ParseException
	 * 
	 */
	@GetMapping()
	public ResponseEntity<MappingJacksonValue> getFirestation(
			@RequestParam(name = "stationNumber", required = true) String station_number) throws ParseException {
		ResponseGetFirestation response = this.firestationService.getFirestation(station_number);
		SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("email", "dossierMedical",
				"age");
		FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonne", monFiltre);
		MappingJacksonValue personnesFiltres = new MappingJacksonValue(response);
		personnesFiltres.setFilters(listDeNosFiltres);
		return ResponseEntity.status(HttpStatus.OK).body(personnesFiltres);
	}

	/**
	 * Ajout d'un mapping caserne/adresse.
	 * 
	 * @param La Firestation que l'on veut ajouter.
	 * 
	 * @return response entity statu OK avec comme body un message pour signaler ce
	 *         qui a ete fait.
	 * 
	 */
	@PostMapping()
	public ResponseEntity<String> addFirestation(@RequestBody Firestation firestation) throws AlreadyExistException {
		this.firestationService.addFirestation(firestation);
		return ResponseEntity.status(HttpStatus.OK).body(
				"La firestation " + firestation.getStation() + " " + firestation.getAddress() + " a bien été ajoutée");
	}

	/**
	 * Mettre à jour le numéro de la caserne de pompiers d'une adresse.
	 * 
	 * @param String du numero de station que l'on veut modifier
	 * @param String de l'address dont on veut changer le numero de station
	 * @param String du nouveau numero de station
	 * 
	 * @return response entity OK avec comme body un message pour signaler ce qui a
	 *         ete fait.
	 * 
	 * @throws RessourceNotFoundException
	 * @throws AlreadyExistException 
	 * 
	 */
	@PutMapping()
	public ResponseEntity<String> updateFirestation(@RequestParam(name = "station", required = true) String station,
			@RequestParam(name = "address", required = true) String address, @RequestParam(name = "newStation", required = true) String newStation)
			throws RessourceNotFoundException, AlreadyExistException {
		this.firestationService.updateFirestation(station, address, newStation);
		return ResponseEntity.status(HttpStatus.OK).body(
				"Le numero de station à l'address: " + address + " est maintenant: " + newStation);
	}

	/**
	 * Supprimer le mapping d'une caserne ou d'une adresse.
	 * 
	 * @param String du numero de station que l'on veut supprimer
	 * @param String de l'address que l'on veut supprimer
	 * 
	 * @return response entity OK avec comme body un message pour signaler ce qui a
	 *         ete fait.
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteFirestation(@RequestParam(name = "station", required = false) String station,
			@RequestParam(name = "address", required = false) String address) throws RessourceNotFoundException {
		if(station!=null && address!=null) {
			this.firestationService.deleteStation(station);
			this.firestationService.deleteAddress(address);
			return ResponseEntity.status(HttpStatus.OK).body("La Station: " + station + " et l'Address: " + address + " ont bien été supprimés");
		} else if(station!=null) {
			this.firestationService.deleteStation(station);
			return ResponseEntity.status(HttpStatus.OK).body("La Station: " + station + " a bien été supprimée");
		} else if(address!=null) {
			this.firestationService.deleteAddress(address);
			return ResponseEntity.status(HttpStatus.OK).body("L'Address: " + address + " a bien été supprimée");
		} 
		return ResponseEntity.status(HttpStatus.OK).body("Veuillez spécifié une station, une address ou les deux");
	}

}
