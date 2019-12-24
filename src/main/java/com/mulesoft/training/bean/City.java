package com.mulesoft.training.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table( name="City",
uniqueConstraints=
    @UniqueConstraint(columnNames={"lattitude","longitude"})
)
public class City {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cityId;
	
	@Column(name = "city_name",unique=true)
	@NotNull
	private String cityName;
	
	@Column(name = "lattitude")
	@NotNull
	private Double lattitude;
	
	@Column(name = "longitude")
	@NotNull
	private Double longitude;
	
	public City() {
		// TODO Auto-generated constructor stub
	}

	public City(Integer cityId,@NotNull String cityName, @NotNull Double lattitude, @NotNull Double longitude) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Double getLattitude() {
		return lattitude;
	}

	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", cityName=" + cityName + ", lattitude=" + lattitude + ", longitude="
				+ longitude + "]";
	}

	

}
