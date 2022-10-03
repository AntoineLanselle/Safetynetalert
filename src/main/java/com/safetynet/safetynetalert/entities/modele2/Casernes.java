package com.safetynet.safetynetalert.entities.modele2;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.entities.modele1.Person;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Antoine
 *
 */
@Getter
@Setter
public class Casernes {

	public Map<String, List<Famille>> casernes;

	/**
	 * Construit un objet casernes contenant toutes les informations triées des
	 * listes passées en parametres.
	 * 
	 * @param la liste d'informations des firestations contenu dans le Json.
	 * @param la liste d'informations des persons contenu dans le Json.
	 * @param la liste d'informations des medicalRecords contenu dans le Json.
	 *
	 */
	public Casernes(List<Firestation> firestationList, List<Person> personList, List<Medicalrecord> medicalrecordList)
			throws ParseException {

		// Créer l'objet Casernes
		this.setCasernes(new HashMap<String, List<Famille>>());

		for (Firestation firestation : firestationList) {
			if (this.getCasernes().containsKey(firestation.getStation())) {
				Famille famille = new Famille(firestation.getAddress());
				if (!this.getCasernes().get(firestation.getStation()).contains(famille)) {
					// si la clé de la firestation existe, on ajoute l'adresse
					this.getCasernes().get(firestation.getStation()).add(famille);
				}
			} else {
				// si la clé n'exite pas on ajoute la clé et l'adresse
				this.getCasernes().put(firestation.getStation(), new ArrayList<Famille>());
				Famille famille = new Famille(firestation.getAddress());
				this.getCasernes().get(firestation.getStation()).add(famille);
			}
		}

		// Ajouter les personnes dans leur Famille
		for (Person person : personList) {
			for (Entry<String, List<Famille>> caserne : this.getCasernes().entrySet()) {
				for (Famille famille : caserne.getValue()) {
					if (famille.getAddress().equals(person.getAddress())) {
						// je créé une personne et l'ajoute à la famille
						famille.ajouterMembre(person, medicalrecordList);
						break;
					}
				}
			}
		}
	}

	/**
	 * Construit un objet casernes contenant uniquement une caserne en particulier
	 * specifiée en parametres.
	 *
	 * @param le numero de la caserne que l on veut
	 * @param la liste d'informations des firestations contenu dans le Json.
	 * @param la liste d'informations des persons contenu dans le Json.
	 * @param la liste d'informations des medicalRecords contenu dans le Json.
	 *
	 */
	public Casernes(String firestation_number, List<Firestation> firestationList, List<Person> personList,
			List<Medicalrecord> medicalrecordList) throws ParseException {

		this.setCasernes(new HashMap<String, List<Famille>>());
		this.getCasernes().put(firestation_number, new ArrayList<Famille>());

		for (Firestation firestation : firestationList) {
			if (firestation_number.equals(firestation.getStation())) {
				Famille famille = new Famille(firestation.getAddress());
				this.getCasernes().get(firestation_number).add(famille);
			}
		}

		for (Person person : personList) {
			for (Famille famille : casernes.get(firestation_number)) {
				if (famille.getAddress().equals(person.getAddress())) {
					// je créé une personne et l'ajoute à la famille
					famille.ajouterMembre(person, medicalrecordList);
					break;
				}
			}

		}

	}

	/**
	 * Trouve les numeros de casernes qui deservent une addresse donnée.
	 * 
	 * @param l'adresse recherchée.
	 * @param une liste des firestations dans la quelle on souhaite chercher l'adresse.
	 * 
	 * @return une liste de numero de casernes qui deservent l'adresse passée en
	 *         paramatres.
	 * 
	 */
	public List<String> trouverCaserneQuiProtege(String address, List<Firestation> firestationList) {

		List<String> casernesQuiProtege = new ArrayList<String>();

		for (Firestation firestation : firestationList) {
			if (firestation.getAddress().equals(address)) {
				casernesQuiProtege.add(firestation.getStation());
			}
		}

		return casernesQuiProtege;
	}

}
