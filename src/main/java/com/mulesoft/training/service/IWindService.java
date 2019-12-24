package com.mulesoft.training.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;

import com.mulesoft.training.bean.Wind;


public interface IWindService {

	public List<Wind> findByCityName(String cityName);

	public Wind findByCityNameAndDate(String cityName, Date date);

	public Wind addWindRecord(String cityName, @Valid Wind wind)throws DataIntegrityViolationException ;

	public Wind updateWindRate(String cityName, Date date, @Valid Wind windRequest);

	public String deleteRecordByCityName(String cityName);
	
	public String deletePurticularRecordByCityNameAndDate(String cityName,Date date);

	public List<Wind> convertAllCitiesWindRate(String unit);

	public List<Wind> findAllDataOfWind();


	

}
