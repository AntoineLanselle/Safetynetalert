package com.safetynet.safetynetalert.services;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.entities.modele1.Person;
import com.safetynet.safetynetalert.entities.modele2.Casernes;
import com.safetynet.safetynetalert.entities.modele2.Famille;
import com.safetynet.safetynetalert.entities.modele2.Personne;
import com.safetynet.safetynetalert.entities.response.ResponseGetFirestation;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;
import com.safetynet.safetynetalert.repository.FirestationRepository;
import com.safetynet.safetynetalert.repository.MedicalrecordRepository;
import com.safetynet.safetynetalert.repository.PersonRepository;

/**
 * @author Antoine
 *
 */
@Service
public class FirestationServiceImpl implements FirestationService {

	private static final Logger LOGGER = LogManager.getLogger(FirestationServiceImpl.class);

	@Autowired
	private FirestationRepository firestationRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private MedicalrecordRepository medicalrecordRepository;

	/**
	 * Recupere toutes les données des Firestations du fichier Json.
	 *
	 * @return une liste de toutes les Firestations du fichier Json.
	 * 
	 */
	@Override
	public List<Firestation> getFirestations() {
		return this.firestationRepository.getAllFirestations();
	}

	/**
	 * Retourne une liste des personnes couvertes par la caserne de pompiers
	 * correspondante.
	 * 
	 * @param le numero de la caserne sous forme d'un String.
	 * 
	 * @return un objet ResponseGetFirestation qui contient une liste des personnes
	 *         couvertes ainsi que le nombre d'enfant et d'adultes.
	 * 
	 * @throws ParseException
	 * 
	 */
	@Override
	public ResponseGetFirestation getFirestation(String station_number) throws ParseException {

		// 1 - récupérer les données du modèle V1
		List<Firestation> firestationList = firestationRepository.getAllFirestations();
		List<Person> personList = personRepository.getAllPersons();
		List<Medicalrecord> medicalrecordList = medicalrecordRepository.getAllMedicalrecords();

		// 2 - J'initialiser mon modèle depuis Casernes du modèle v2
		Casernes casernes = new Casernes(firestationList, personList, medicalrecordList);

		ResponseGetFirestation response = new ResponseGetFirestation();

		// 3 = je trie mes valeur à retourner
		if (casernes.getCasernes().containsKey(station_number)) {

			List<Famille> famillesCouvertes = casernes.getCasernes().get(station_number);

			for (Famille famille : famillesCouvertes) {
				response.setNombreAdultes(response.getNombreAdultes() + famille.getNombreAdulte());
				response.setNombreEnfants(response.getNombreEnfants() + famille.getNombreEnfant());
				for (Personne personne : famille.getMembres()) {
					response.personnesCouvertes.add(personne);
				}
			}

		}
		// 4 - je retourne mes valeurs
		return response;
	}

	/**
	 * Ajout d'un mapping caserne/adresse.
	 * 
	 * @param La Firestation que l'on veut ajouter.
	 * 
	 * @throws AlreadyExistException
	 * 
	 */
	@Override
	public void addFirestation(Firestation firestation) throws AlreadyExistException {
		Firestation newFirestation = new Firestation();
		newFirestation = this.firestationRepository.findStationAddress(firestation.getStation(),
				firestation.getAddress());
		if (newFirestation.getStation() != null) {
			String error = String.format("Station: %s - Address: %s already exist", firestation.getStation(),
					firestation.getAddress());
			LOGGER.error(error);
			throw new AlreadyExistException(error);
		}
		this.firestationRepository.addFirestation(firestation);
	}

	/**
	 * Mettre à jour le numéro de la caserne de pompiers d'une adresse.
	 * 
	 * @param String du numero de station que l'on veut modifier
	 * @param String de l'address dont on veut changer le numero de station
	 * @param String du nouveau numero de station
	 * 
	 * @throws RessourceNotFoundException
	 * @throws AlreadyExistException
	 * 
	 */
	@Override
	public void updateFirestation(String station, String address, String newStation)
			throws RessourceNotFoundException, AlreadyExistException {
		Firestation firestation = this.firestationRepository.findStationAddress(station, address);
		if (firestation.getStation() != null) {
			Firestation newfirestation = this.firestationRepository.findStationAddress(newStation, address);
			if (newfirestation.getStation() == null) {
				firestation.setStation(newStation);
			} else {
				String error = String.format("Station: %s - Address: %s already exist", newStation, address);
				LOGGER.error(error);
				throw new AlreadyExistException(error);
			}
		} else {
			String error = String.format("Station: %s - Address: %s not found", station, address);
			LOGGER.error(error);
			throw new RessourceNotFoundException(error);
		}
	}

	/**
	 * Supprimer le mapping d'une station dans le repository.
	 * 
	 * @param Le numero de Station que l'on veut supprimer
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@Override
	public void deleteStation(String station) throws RessourceNotFoundException {

		List<Firestation> listByStation = this.firestationRepository.findByStation(station);

		if (listByStation.size() > 0) {
			for (Firestation fireStation : listByStation) {
				this.firestationRepository.delete(fireStation);
			}
		} else {
			String error = String.format("Station %s not found", station);
			LOGGER.error(error);
			throw new RessourceNotFoundException(error);
		}

	}

	/**
	 * Supprimer une adresse dans le repository.
	 *
	 * @param L'address que l'on veut supprimer
	 * 
	 * @throws RessourceNotFoundException
	 * 
	 */
	@Override
	public void deleteAddress(String address) throws RessourceNotFoundException {

		List<Firestation> listByAddress = this.firestationRepository.findByAddress(address);

		if (listByAddress.size() > 0) {
			for (Firestation fireStation : listByAddress) {
				this.firestationRepository.delete(fireStation);
			}
		} else {
			String error = String.format("Address %s not found", address);
			LOGGER.error(error);
			throw new RessourceNotFoundException(error);
		}

	}
}
