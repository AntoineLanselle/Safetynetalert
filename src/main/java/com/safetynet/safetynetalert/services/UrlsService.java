package com.safetynet.safetynetalert.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.safetynet.safetynetalert.entities.modele2.Famille;
import com.safetynet.safetynetalert.entities.modele2.Personne;
import com.safetynet.safetynetalert.entities.response.ResponseChildAlert;
import com.safetynet.safetynetalert.entities.response.ResponseFire;

/**
 * @author Antoine
 *
 */
public interface UrlsService {

	public ResponseChildAlert childAlert(String address) throws IOException, ParseException;

	public List<String> phoneAlert(String firestation_number) throws IOException, ParseException;

	public ResponseFire fire(String address) throws IOException, ParseException;

	public List<Famille> floodStations(List<String> station_numbers) throws IOException, ParseException;

	public List<Personne> personInfo(String firstName, String lastName) throws IOException, ParseException;

	public List<String> communityEmail(String city) throws IOException;

}
