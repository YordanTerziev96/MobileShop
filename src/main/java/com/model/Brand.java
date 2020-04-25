package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Brand extends BaseEntity{

	@Column(nullable = false)
	private String brand;
	
	@JsonIgnore
	@OneToMany(mappedBy = "brand")
	private List<Model> models = new ArrayList<>();

	public String getBrand() {
		return brand;
	}

	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
	
}
