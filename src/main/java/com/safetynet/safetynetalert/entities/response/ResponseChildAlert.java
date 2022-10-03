package com.safetynet.safetynetalert.entities.response;

import java.util.ArrayList;
import java.util.List;

import com.safetynet.safetynetalert.entities.modele2.Personne;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Antoine
 *
 */
@Getter
@Setter
public class ResponseChildAlert {

	public List<Personne> enfants;
	public List<Personne> autresMembres;

	/**
	 * Construit une reponse à l'url ChildAlert.
	 * 
	 */
	public ResponseChildAlert() {
		this.enfants = new ArrayList<Personne>();
		this.autresMembres = new ArrayList<Personne>();
	}

}
