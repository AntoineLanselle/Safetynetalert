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
public class ResponseFire {

	public List<Personne> personnesVivantAddress;
	public List<String> casernesDeservantAdress;

	/**
	 * Construit une reponse à l'url Fire.
	 * 
	 */
	public ResponseFire() {
		this.personnesVivantAddress = new ArrayList<Personne>();
		this.casernesDeservantAdress = new ArrayList<String>();
	}

}
