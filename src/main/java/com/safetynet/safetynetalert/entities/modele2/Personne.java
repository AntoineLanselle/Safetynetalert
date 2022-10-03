package com.safetynet.safetynetalert.entities.modele2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
@JsonFilter("filtreDynamiquePersonne")
public class Personne {

	public String firstName;
	public String lastName;
	public String address;
	public String phone;
	public String email;
	public DossierMedical dossierMedical;
	public int age;

	/**
	 * Construit la personne dont les infos (person) ont été passé en parametre et
	 * son dossier medical.
	 * 
	 * @param les informations du Json consernant la personne que l'on veut
	 *            construire.
	 * @param les informations du Json concernant les données medicales.
	 * 
	 */
	public Personne(Person person, List<Medicalrecord> medicalrecordList) throws ParseException {
		this.setFirstName(person.getFirstName());
		this.setLastName(person.getLastName());
		this.setAddress(person.getAddress());
		this.setPhone(person.getPhone());
		this.setEmail(person.getEmail());
		this.setDossierMedical(new DossierMedical(findMedicalrecord(medicalrecordList)));
	}

	/**
	 * Construit la personne dont les infos (person) ont été passé en parametre.
	 * 
	 * @param les informations du Json consernant la personne que l'on veut
	 *            construire.
	 * 
	 */
	public Personne(Person person) {
		this.setFirstName(person.getFirstName());
		this.setLastName(person.getLastName());
		this.setAddress(person.getAddress());
		this.setPhone(person.getPhone());
		this.setEmail(person.getEmail());
	}

	/**
	 * Trouve le dossier medical de la personne.
	 * 
	 * @param une liste de données medicales.
	 * 
	 * @return le dossier medical de la personne si il est dans la liste passé en
	 *         parametre, null sinon.
	 * 
	 */
	public Medicalrecord findMedicalrecord(List<Medicalrecord> medicalrecordList) throws ParseException {
		for (Medicalrecord medicalrecord : medicalrecordList) {
			if (this.getFirstName().equals(medicalrecord.getFirstName())
					&& this.getLastName().equals(medicalrecord.getLastName())) {
				this.setAge(calculeAge(medicalrecord.getBirthdate()));
				return medicalrecord;
			}
		}
		return null;
	}

	/**
	 * Calcule un age en années à partir d'un String contenant une date sous la
	 * forme MM/dd/yyyy.
	 * 
	 * @param la date de naissance de la personne sous la forme d'une chaine de
	 *           caractere MM/dd/yyyy.
	 * 
	 * @return un int de l'age de la personne.
	 * 
	 */
	private int calculeAge(String birthDate) throws ParseException {

		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		LocalDate today = LocalDate.now();
		LocalDate birthday = simpleDateFormat.parse(birthDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		Period age = Period.between(birthday, today);

		return age.getYears();
	}

}
