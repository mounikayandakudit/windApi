package com.mulesoft.training.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table( name="Wind",
	    uniqueConstraints=
	        @UniqueConstraint(columnNames={"date","city_id"})
	)
@Entity
public class Wind {
	@Id
	@Column(name = "wind_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer windId;
		
	@Column(name = "wind_rate")
	@NotNull
	private Double windRate;
	
	@Column(name = "date")
	@NotNull
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private City city;
	
	public Wind() {
		// TODO Auto-generated constructor stub
	}

	public Wind(Integer windId, @NotNull Double windRate, @NotNull Date date, City city) {
		super();
		this.windId = windId;
		this.windRate = windRate;
		this.date = date;
		this.city = city;
	}

	public Integer getWindId() {
		return windId;
	}

	public void setWindId(Integer windId) {
		this.windId = windId;
	}

	public Double getWindRate() {
		return windRate;
	}

	public void setWindRate(Double windRate) {
		this.windRate = windRate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Wind [windId=" + windId + ", windRate=" + windRate + ", date=" + date + ", city=" + city + "]";
	}
	
	
	
	
	
}
	
	
	
	