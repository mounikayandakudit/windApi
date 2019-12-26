package com.mulesoft.training.controller;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mulesoft.training.bean.City;
import com.mulesoft.training.bean.Wind;
import com.mulesoft.training.dao.WindRepository;
import com.mulesoft.training.exception.ResourceNotFoundException;
import com.mulesoft.training.exception.Validation;
import com.mulesoft.training.service.ICityService;
import com.mulesoft.training.service.IWindService;



@RestController
@RequestMapping("/api")
public class WeatherController {
	
	@Autowired
	private ICityService cityService;
	
	@Autowired  
	private IWindService windService;
	
	@GetMapping("/city")
    public ResponseEntity<List<City>> getAllCities() {
		List<City> result = cityService.findAllCitites();
        return new ResponseEntity<List<City>>(result,HttpStatus.OK);
    }
	
	@GetMapping("/city/{cityName}")
	public ResponseEntity<?> getCityDetailsByCityName(@PathVariable String cityName) {
		try {
			City result = cityService.getCityDetailsCityName(cityName);
			return new ResponseEntity<City>(
	    		result,HttpStatus.OK);
		}
		catch(ResourceNotFoundException error){
			return new ResponseEntity<String>(
		    		error.getMessage(),HttpStatus.NOT_FOUND);
		}				
	}

	 @PostMapping("/city")
	    public ResponseEntity<?> createCity(@Valid @RequestBody City city) {
		 try { 
				City result = cityService.addCity(city);
				return  new ResponseEntity<City>(
			    	      result,HttpStatus.CREATED);
			}
			catch (Validation error) {
				return  new ResponseEntity<String>(
			    	      error.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}
			catch (DataIntegrityViolationException error) {
				return new ResponseEntity<String>(
						error.getMessage(),HttpStatus.NOT_ACCEPTABLE);				
			}
	  }
	 
	 @PutMapping("/city/{cityName}")
	    public ResponseEntity<?> updateCity(@PathVariable String cityName,@Valid @RequestBody City cityRequest) {
	        try {
				City result = cityService.updateCity(cityName, cityRequest);
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.set("result", "city updated successfully");
				return new ResponseEntity<City>(
			    	    result,responseHeaders,HttpStatus.OK);
	        }
	        catch (Validation error) {
				return  new ResponseEntity<String>(
			    	      error.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}
	        catch (ResourceNotFoundException error) {
				return  new ResponseEntity<String>(
			    	      error.getMessage(),HttpStatus.BAD_REQUEST);
			}
	    }
	 
	 @DeleteMapping("/city/{cityName}")
	    public ResponseEntity<String> deleteCity(@PathVariable String cityName) {
	       try {
	    	   return new ResponseEntity<String>(
			    	      cityService.deleteCity(cityName),HttpStatus.OK);
	       }
	       catch (ResourceNotFoundException error) {
				return  new ResponseEntity<String>(
			    	      error.getMessage(),HttpStatus.BAD_REQUEST);
			}
	    }
	 
	 
	 
	 
	 ///wind controller class///
	 
	 @GetMapping("/city/{cityName}/showRecord")
	    public ResponseEntity<?> getAllWindRateByCityName(@PathVariable (value = "cityName") String cityName,@RequestParam(required = false) String unit) {
		 	try {
		 		List<Wind> result = windService.findByCityName(cityName,unit);
		 		return new ResponseEntity<List<Wind>>(
	        		result,HttpStatus.OK);
		 	}
		 	catch (ResourceNotFoundException error) {
		 		return new ResponseEntity<String>(
		        		error.getMessage(),HttpStatus.NOT_FOUND);
			}
	  }
	 
	 @GetMapping("/city/{cityName}/{date}")
	    public ResponseEntity<?> getWindRateByCityNameAndDate(@PathVariable (value = "cityName") String cityName,@PathVariable (value = "date") Date date) {
		 	try{
		 		Wind result = windService.findByCityNameAndDate(cityName,date);
		 		return new ResponseEntity<Wind>(
	        		result,HttpStatus.OK);
		 	}
		 	catch (ResourceNotFoundException error) {
		 		return new ResponseEntity<String>(
		        		error.getMessage(),HttpStatus.NOT_FOUND);
			}
	  }
	 
	 @PostMapping("/city/{cityName}/addRecord")
	    public ResponseEntity<?> createWindRecord(@PathVariable (value = "cityName") String cityName,
	                                 @Valid @RequestBody Wind wind) {
		 	try { 
				Wind result = windService.addWindRecord(cityName,wind);
				return new ResponseEntity<Wind>(
			    	      result,HttpStatus.CREATED);
			}
			catch (DataIntegrityViolationException error) {
				return new ResponseEntity<String>(
			    	      "Record already exist",HttpStatus.NOT_ACCEPTABLE);
			}
			catch (ResourceNotFoundException error) {
				return new ResponseEntity<String>(
			    	      error.getMessage(),HttpStatus.BAD_REQUEST);
			}
	    }
	 
	 @PutMapping("/city/{cityName}/records/{date}")
	  public ResponseEntity<?> updateWindRecord(@PathVariable (value = "cityName") String cityName,
              @PathVariable (value = "date") Date date,
              @Valid @RequestBody Wind windRequest) {
	        try {
				Wind result = windService.updateWindRate(cityName,date,windRequest);
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.set("result", "wind record update");
				return new ResponseEntity<Wind>(
			    	    result,responseHeaders,HttpStatus.OK);
	        }
	        catch (ResourceNotFoundException error) {
				return  new ResponseEntity<String>(
			    	      error.getMessage(),HttpStatus.BAD_REQUEST);
			}
	    }
	 
	 @DeleteMapping("/city/{cityName}/records")
		public ResponseEntity<String> deleteWindRecordByCityName(@PathVariable String cityName) {
		       try {
		    	   return new ResponseEntity<String>(
				    	      windService.deleteRecordByCityName(cityName),HttpStatus.OK);
		       }
		       catch (ResourceNotFoundException error) {
					return  new ResponseEntity<String>(
				    	      error.getMessage(),HttpStatus.BAD_REQUEST);
				}
	 }
	 
	 @DeleteMapping("/city/{cityName}/{date}")
		public ResponseEntity<String> deleteWindRecordByCityNameAndDate(@PathVariable String cityName,@PathVariable Date date) {
		       try {
		    	   return new ResponseEntity<String>(
				    	      windService.deletePurticularRecordByCityNameAndDate(cityName, date),HttpStatus.OK);
		       }
		       catch (ResourceNotFoundException error) {
					return  new ResponseEntity<String>(
				    	      error.getMessage(),HttpStatus.BAD_REQUEST);
				}
	}
	 
	/* @GetMapping("/city/{unit}/units")
		public ResponseEntity<?> getAllCitiesWindRateInPurticularUnit(@PathVariable String unit){
			try{
				List<Wind> result = windService.convertAllCitiesWindRate(unit);
				return new ResponseEntity<List<Wind>>(
		    		result,HttpStatus.OK);
			}
			catch(ResourceNotFoundException error) {
				return new ResponseEntity<String>(
				error.getMessage(),HttpStatus.BAD_REQUEST);
			}
		}*/
	 
	 @GetMapping("/city/windData")
		public ResponseEntity<?> getAllWindRateRecords(){
			try{
				List<Wind> result = windService.findAllDataOfWind();
				return new ResponseEntity<List<Wind>>(
		    		result,HttpStatus.OK);
			}
			catch(ResourceNotFoundException error) {
				return new ResponseEntity<String>(
				error.getMessage(),HttpStatus.NOT_FOUND);
			}
		}
	 
}
