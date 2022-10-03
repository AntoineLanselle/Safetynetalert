package com.safetynet.safetynetalert.services;

import java.text.ParseException;
import java.util.List;

import com.safetynet.safetynetalert.entities.modele1.Firestation;
import com.safetynet.safetynetalert.entities.response.ResponseGetFirestation;
import com.safetynet.safetynetalert.exception.AlreadyExistException;
import com.safetynet.safetynetalert.exception.RessourceNotFoundException;

/**
 * @author Antoine
 *
 */
public interface FirestationService {

	public List<Firestation> getFirestations();

	public ResponseGetFirestation getFirestation(String station) throws ParseException;

	public void addFirestation(Firestation firestation) throws AlreadyExistException;

	public void updateFirestation(String station, String address, String newStation) throws RessourceNotFoundException, AlreadyExistException;

	public void deleteStation(String station) throws RessourceNotFoundException;

	public void deleteAddress(String address) throws RessourceNotFoundException;
}
