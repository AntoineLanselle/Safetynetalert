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
public class ResponseGetFirestation {

	public List<Personne> personnesCouvertes;
	public int nombreAdultes;
	public int nombreEnfants;

	/**
	 * Construit une reponse à l'url GetFirestation.
	 * 
	 */
	public ResponseGetFirestation() {
		this.personnesCouvertes = new ArrayList<Personne>();
		this.setNombreAdultes(0);
		this.setNombreEnfants(0);
	}

}
