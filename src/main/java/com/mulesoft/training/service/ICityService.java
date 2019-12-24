package com.mulesoft.training.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mulesoft.training.bean.City;



public interface ICityService {

	public List<City> findAllCitites();

	public City getCityDetailsCityName(String cityName);

	public City addCity(@Valid City city);

	public City updateCity(String cityName, @Valid City cityRequest);

	public String deleteCity(String cityName);

	

}
