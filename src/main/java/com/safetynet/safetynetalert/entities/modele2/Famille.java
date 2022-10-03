package com.safetynet.safetynetalert.entities.modele2;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
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
@JsonFilter("filtreDynamiqueFamille")
public class Famille {

	public String address;
	public String city;
	public int nombreEnfant;
	public int nombreAdulte;
	public List<Personne> membres;

	/**
	 * Construit une famille à partir d une adresse donnée en paramaetre.
	 * 
	 * @param une chaine de caractere correspondant à l'addresse de la famille.
	 *
	 */
	public Famille(String address) {
		this.setAddress(address);
		this.setMembres(new ArrayList<Personne>());
	}

	/**
	 * Ajoute un membre passé en parametre à la famille, à partir de la personne et
	 * son dossier medical.
	 * 
	 * @param la person que l'on veut ajouter
	 * @param la liste des donnees medicales 
	 *
	 */
	public void ajouterMembre(Person person, List<Medicalrecord> medicalrecordList) throws ParseException {

		// On créé une nouvelle personne qu'on ajoute à la famille
		Personne personne = new Personne(person, medicalrecordList);
		this.membres.add(personne);

		// On met à jour les compteurs de majeurs et de mineurs
		if (personne.getAge() <= 18) {
			this.nombreEnfant++;
		} else {
			this.nombreAdulte++;
		}
	}

	/**
	 * Trouve les membres de cette famille dans une liste de personne et les ajoute
	 * à la famille avec leur dossier medical.
	 * 
	 * @param la liste des informations des personnes contenus dans le Json.
	 * @param la liste de tous les dossiers medicaux.
	 *
	 */
	public void trouverMembres(List<Person> personList, List<Medicalrecord> medicalrecordList) throws ParseException {
		for (Person person : personList) {
			if (this.getAddress().equals(person.getAddress())) {
				this.ajouterMembre(person, medicalrecordList);
			}
		}
	}

	/**
	 * Determine si l'objet passé en parametre est le même que celui-ci.
	 * 
	 * @param l'objet à comparé.
	 * 
	 * @return true si l'objet passé en parametre est une Famille logé à la meme
	 *         adresse que celle-ci, false sinon.
	 *
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Famille))
			return false;
		Famille other = (Famille) obj;
		if (other.getAddress().equals(this.getAddress()))
			return true;
		return false;
	}

}
