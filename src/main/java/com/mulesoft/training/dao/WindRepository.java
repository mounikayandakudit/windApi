package com.mulesoft.training.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.mulesoft.training.bean.City;
import com.mulesoft.training.bean.Wind;

@Repository
public interface WindRepository extends JpaRepository<Wind, Integer>{

	@Query("SELECT w FROM Wind w WHERE w.city.cityName = :cityName")
	List<Wind> findByCityName(String cityName);

	@Query("SELECT w FROM Wind w WHERE w.city.cityName = :cityName AND w.date = :date")
	Wind findByCityNameAndDate(String cityName, Date date);

	/*@Transactional
	@Modifying
	@Query("DELETE FROM Wind w WHERE w.city.cityName = :cityName")
	void deleteByCityName(String cityName);

	@Transactional
	@Modifying
	@Query("DELETE FROM Wind w WHERE w.city.cityName = :cityName AND w.date = :date")
	void deleteByCityNameAndDate(String cityName, Date date);*/

	@Query("SELECT w FROM Wind w WHERE w.city.cityName = :cityName AND w.date = :date")
	Optional<Wind> findByCityAndDate(String cityName, Date date);

	@Query("SELECT w FROM Wind w WHERE w.city.cityName = :cityName")
	Optional<List<Wind>> findByCityNameAllRecords(String cityName);

	
	

}
