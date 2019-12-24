package com.mulesoft.training.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mulesoft.training.bean.City;
import com.mulesoft.training.dao.CityRepository;
import com.mulesoft.training.dao.WindRepository;
import com.mulesoft.training.exception.ResourceNotFoundException;
import com.mulesoft.training.exception.Validation;

@Service
@Transactional
public class CityService  implements ICityService{

	@Autowired
	private CityRepository cityRepository;
	
	@Override
	public List<City> findAllCitites() {
		List<City> result = cityRepository.findAll();
		return result;
	}

	@Override
	public City getCityDetailsCityName(String cityName) {
		return cityRepository.findByCityName(cityName).map(
				city ->{
					return city;
				}).orElseThrow(() -> new ResourceNotFoundException("CityName " + cityName + " not found"));		
	}

	@Override
	public City addCity(@Valid City city) {
		validateCity(city.getCityName());	//validate cityName
		validateID(city.getCityId());	//validate Id
		validateLongitudeAndLattitude(city.getLongitude(),city.getLattitude());	//validate longitude and lattitude
		City entity = cityRepository.save(city);
		return entity;		
	}
	
	@Override
	public City updateCity(String cityName, @Valid City city) {
		return cityRepository.findByCityName(cityName).map(cityEntity -> {
			validateLongitudeAndLattitude(city.getLongitude(), city.getLattitude());
			cityEntity.setLattitude(city.getLattitude());
			cityEntity.setLongitude(city.getLongitude());
            return cityRepository.save(cityEntity);
        }).orElseThrow(() -> new ResourceNotFoundException("CityName " + cityName + " not found"));
	}
	
	@Override
	public String deleteCity(String cityName) {
		 return cityRepository.findByCityName(cityName).map(city -> {
			  	cityRepository.delete(city);
	            return "City deleted successfully";
	      }).orElseThrow(() -> new ResourceNotFoundException("CityName " + cityName + " not found"));
	}

	
	///Validation///
	
	private Integer validateID(Integer cityId) {
		Optional<City> city = cityRepository.findById(cityId);
		if(city.isEmpty()) {
			return cityId;
		}
		else {
			throw new Validation("ID already exist.");
		}
		
	}

	private boolean validateLongitudeAndLattitude(Double longitude, Double lattitude) {
		City city = cityRepository.findByLongitudeAndLattitude(longitude,lattitude);
		if(city==null) {
			return true;
		}
		else {
			throw new DataIntegrityViolationException("Longitude and Lattitude value Not be same");
		}
		
	}

	private boolean validateCity(String cityName) {
		Optional<City> city = cityRepository.findByCityName(cityName);
		if(city.isEmpty()) {
			return true;
		}
		else {
			throw new Validation("City already exist");
		}
		
	}

	
	
	

}
