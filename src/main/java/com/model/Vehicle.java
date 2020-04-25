package com.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Vehicle extends BaseEntity{
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column
	private String description;
	
	@Column(nullable = false)
	private int year;
	
	@Column(nullable = false)
	private int horsePower;
	
	@Column(nullable = false)
	private BigDecimal kmDriven;
	
	@JsonIgnore
	@OneToMany(mappedBy = "vehicle")
	private List<Image> images;
	
	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		for(Image i : images){
			this.images.add(i);
		}
	}

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private String model;
	
	@Enumerated(EnumType.STRING) 
	private EngineTypeEnum engineType;
	
	@Enumerated(EnumType.STRING) 
	private GearboxEnum gearboxType;
	
	@Enumerated(EnumType.STRING) 
	private CategoryEnum categoryType;
	
	@Enumerated(EnumType.STRING) 
	private VehicleTypeEnum type;
	
	@Column(nullable = false)
	private String region;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL,fetch= FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	private List<ExtratypeEnum> extras;


	public List<ExtratypeEnum> getExtras() {
		return extras;
	}

	public void setExtras(List<ExtratypeEnum> extras) {
		this.extras = extras;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public int getYear() {
		return year;
	}


	public int getHorsePower() {
		return horsePower;
	}


	public BigDecimal getKmDriven() {
		return kmDriven;
	}

	public String getBrand() {
		return brand;
	}


	public String getModel() {
		return model;
	}


	public EngineTypeEnum getEngineType() {
		return engineType;
	}

	public GearboxEnum getGearboxType() {
		return gearboxType;
	}


	public CategoryEnum getCategoryType() {
		return categoryType;
	}

	public VehicleTypeEnum getType() {
		return type;
	}


	public String getRegion() {
		return region;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}

	public void setKmDriven(BigDecimal kmDriven) {
		this.kmDriven = kmDriven;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setEngineType(EngineTypeEnum engineType) {
		this.engineType = engineType;
	}

	public void setGearboxType(GearboxEnum gearboxType) {
		this.gearboxType = gearboxType;
	}

	public void setCategoryType(CategoryEnum categoryType) {
		this.categoryType = categoryType;
	}

	public void setType(VehicleTypeEnum type) {
		this.type = type;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
	

}
