package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Model extends BaseEntity{

	@Column(nullable = false)
	private String model;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;
	

	public String getModel() {
		return model;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	
}
