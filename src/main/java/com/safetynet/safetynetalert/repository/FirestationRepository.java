package com.safetynet.safetynetalert.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.safetynetalert.entities.modele1.Firestation;

/**
 * @author Antoine
 *
 */
@Repository
public class FirestationRepository {

	private List<Firestation> listFirestation = new ArrayList<>();

	/**
	 * renvoi le contenu de ce Repository.
	 * 
	 * @return une liste de Firestations.
	 * 
	 */
	public List<Firestation> getAllFirestations() {
		return this.listFirestation;
	}

	/**
	 * ajoute une Firestation au Respository.
	 * 
	 * @param une Firestation que l'on veut ajouter.
	 * 
	 * @return un boolean true si l'action a bien été faite false sinon.
	 * 
	 */
	public boolean addFirestation(Firestation firestation) {
		return this.listFirestation.add(firestation);
	}

	/**
	 * Trouve une association Firestation station - address dans le Repository.
	 * 
	 * @param un String de la station de la Firestation.
	 * @param un String de l address de la Firestation.
	 * 
	 * @return la Firestation à cette station si elle existe, une nouvelle
	 *         Firestation sinon.
	 * 
	 */
	public Firestation findStationAddress(String station, String address) {
		for (Firestation firestation : this.listFirestation) {
			if (firestation.getStation().equals(station) && firestation.getAddress().equals(address)) {
				return firestation;
			}
		}
		return new Firestation();
	}

	/**
	 * Trouve toutes les Firestation qui ont l'address en parametre dans le Repository.
	 * 
	 * @param un String de l'address des Firestation.
	 * 
	 * @return Une liste de Firestation qui ont pour address l address en
	 *         parametre
	 * 
	 */
	public List<Firestation> findByAddress(String address) {
		List<Firestation> listFirestation = new ArrayList<Firestation>();
		for (Firestation firestation : this.listFirestation) {
			if (firestation.getAddress().equals(address)) {
				listFirestation.add(firestation);
			}
		}
		return listFirestation;
	}
	
	/**
	 * Trouve toutes les Firestation qui ont le numero de station en parametre dans le Repository.
	 * 
	 * @param un String du numero de station des Firestation.
	 * 
	 * @return Une liste de Firestation qui ont pour numero de station la station en
	 *         parametre
	 * 
	 */
	public List<Firestation> findByStation(String station) {
		List<Firestation> listFirestation = new ArrayList<Firestation>();
		for (Firestation firestation : this.listFirestation) {
			if (firestation.getStation().equals(station)) {
				listFirestation.add(firestation);
			}
		}
		return listFirestation;
	}

	/**
	 * Permet de supprimer une Firestation du Repository.
	 * 
	 * @param la Firestation que l'on veut supprimer.
	 * 
	 */
	public void delete(Firestation firestation) {
		this.listFirestation.remove(firestation);
	}

}
