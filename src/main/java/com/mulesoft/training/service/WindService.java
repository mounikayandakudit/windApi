package com.mulesoft.training.service;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mulesoft.training.bean.City;
import com.mulesoft.training.bean.CityNames;
import com.mulesoft.training.bean.Wind;
import com.mulesoft.training.dao.CityRepository;
import com.mulesoft.training.dao.WindRepository;
import com.mulesoft.training.exception.ResourceNotFoundException;
import com.mulesoft.training.exception.Validation;

@Service
@Transactional
public class WindService implements IWindService{

	@Autowired
	private WindRepository windRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Override
	public List<Wind> findAllDataOfWind() {
		Sort sortOrder = Sort.by("windId").ascending();
		List<Wind> result = windRepository.findAll(sortOrder);
		if(!result.isEmpty() ) {
			return result;
		}
		else {
			throw new ResourceNotFoundException("No data found.");
		}
	}
	
	@Override
	public List<Wind> findByCityName(String cityName,String unit) {
		List<Wind> data = windRepository.findByCityName(cityName);
		if(data.isEmpty()) {
			throw new ResourceNotFoundException("CityName " + cityName + " Not Found.");
		}
		else {
			if(unit==null) {
				return data;
			}
			data = convertData(data, unit);
			return data;
		}
	}
	
	@Override
	public Wind findByCityNameAndDate(String cityName, Date date) {
		Wind result = windRepository.findByCityNameAndDate(cityName,date);
		if(result != null ) {
			return result;
		}
		else {
			throw new ResourceNotFoundException("record not found");
		}	
	}

	@Override
	public Wind addWindRecord(String cityName, Wind wind) throws DataIntegrityViolationException {
		
		return cityRepository.findByCityName(cityName).map(city -> {
			wind.setCity(city);		
            return windRepository.save(wind);
        }).orElseThrow(() -> new ResourceNotFoundException("CityName " + cityName + " not found"));
		
	}
	
	
	//validate cityId and date 
	private boolean validateCityAndDate(String cityName, Date date) {
		Wind wind = windRepository.findByCityNameAndDate(cityName,date);
		if(wind==null) {
			return true;
		}
		else {
			throw new DataIntegrityViolationException("Record already present.");
		}
	}

	@Override
	public Wind updateWindRate(String cityName, Date date, @Valid Wind windRequest) {
		if(cityRepository.findByCityName(cityName)==null) {
            throw new ResourceNotFoundException("CityName " + cityName + " not found");
        }
		Wind result = windRepository.findByCityNameAndDate(cityName, date);
		if(result!=null) {
			result.setWindRate(windRequest.getWindRate());
			return windRepository.save(result);
		}
		else {
            throw new ResourceNotFoundException("record not found for " +windRequest.getDate()+ " this date");
		}		
	}

	@Override
	public String deleteRecordByCityName(String cityName) {
		if(cityRepository.findByCityName(cityName)==null) {
            throw new ResourceNotFoundException("CityName " + cityName + " not found");
        }
		return windRepository.findByCityNameAllRecords(cityName).map(winds -> {
            windRepository.deleteAll(winds);
            return "all record deleted of city name " +cityName;
        }).orElseThrow(() -> new ResourceNotFoundException("Wind record not found with city name " + cityName));	
	}

	@Override
	public String deletePurticularRecordByCityNameAndDate(String cityName, Date date) {
		return windRepository.findByCityAndDate(cityName, date).map(winds -> {
            windRepository.delete(winds);
            return "record deleted";
        }).orElseThrow(() -> new ResourceNotFoundException("Wind record not found with city name " + cityName + " and date " + date));
	}

	@Override
	public List<Wind> convertAllCitiesWindRate(String unit) {
		Sort sortByName = Sort.by("windId").ascending();
		List<Wind> actualData = windRepository.findAll(sortByName);
		List<Wind> result = convertData(actualData,unit);
		return result;
	}

	private List<Wind> convertData(List<Wind> data, String unit) {
		List<Wind> new_data = new ArrayList<Wind>(data.size());
		if(unit.equalsIgnoreCase("m_s")) {
			return data;
		}
		else if(unit.equalsIgnoreCase("km_hr")) {
			for (Wind wind : data) {
				Wind entity = new Wind();
				double km_hr = (double) (3.6 * wind.getWindRate());
				entity.setWindId(wind.getWindId());
				entity.setCity(wind.getCity());
				entity.setDate(wind.getDate());
				entity.setWindRate(km_hr);				
				new_data.add(entity);
			}
		}
		else if(unit.equalsIgnoreCase("m_hr")) {
			for (Wind wind : data) {
				Wind entity = new Wind();
				double km_hr = (double) (3.6 * wind.getWindRate());
				double m_hr  = km_hr /  1.609f;
				entity.setWindId(wind.getWindId());
				entity.setCity(wind.getCity());
				entity.setDate(wind.getDate());
				entity.setWindRate(m_hr);				
				new_data.add(entity);
			}
		}
		else {
			throw new ResourceNotFoundException("unit is not valid");
		}
		return new_data;
	}

}
