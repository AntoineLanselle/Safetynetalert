package com.safetynet.safetynetalert.entities.modele1;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Antoine
 *
 */
@Getter
@Setter
public class Medicalrecord {

	public String firstName;
	public String lastName;
	public String birthdate;
	public List<String> medications = null;
	public List<String> allergies = null;

}
