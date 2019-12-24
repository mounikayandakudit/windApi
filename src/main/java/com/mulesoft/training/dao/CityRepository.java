package com.mulesoft.training.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mulesoft.training.bean.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

	Optional<City> findByCityName(String cityName);

	@Query("SELECT c FROM City c WHERE c.longitude = ?1 AND c.lattitude = ?2")
	City findByLongitudeAndLattitude(Double longitude, Double lattitude);
	
	@Query("SELECT w FROM City w WHERE w.cityName = :cityName")
	City findByCity(String cityName);

}
