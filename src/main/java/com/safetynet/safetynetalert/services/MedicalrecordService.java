package com.safetynet.safetynetalert.services;

import java.util.List;

import com.safetynet.safetynetalert.entities.modele1.Medicalrecord;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;

/**
 * @author Antoine
 *
 */
public interface MedicalrecordService {

	public List<Medicalrecord> getMedicalrecords();

	public void addMedicalrecord(Medicalrecord medicalrecord) throws AlreadyExistException;

	public void updateMedicalrecord(Medicalrecord medicalrecord) throws RessourceNotFoundException;

	public void deleteMedicalrecord(String firstName, String lastName) throws RessourceNotFoundException;

}
