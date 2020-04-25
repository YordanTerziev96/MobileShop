package com.model;

import java.math.BigDecimal;
import java.util.List;

public class VehicleDto {
	
	private String fromPrice;
	
	private String toPrice;
	
	private String yearFrom;
	
	private String yearTo;
	
	private String hpFrom;
	
	private String hpTo;

	private String brand;

	private String model;
	
	private String engineType;
	
	private String gearboxType;
	
	private String categoryType;

	private String type;
	
	private String region;
	
	private List<String> extras;

	public String getFromPrice() {
		return fromPrice;
	}

	public String getToPrice() {
		return toPrice;
	}

	public String getYearFrom() {
		return yearFrom;
	}

	public String getYearTo() {
		return yearTo;
	}

	public String getHpFrom() {
		return hpFrom;
	}

	public String getHpTo() {
		return hpTo;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public String getEngineType() {
		return engineType;
	}

	public String getGearboxType() {
		return gearboxType;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public String getType() {
		return type;
	}

	public String getRegion() {
		return region;
	}

	public List<String> getExtras() {
		return extras;
	}

	public void setFromPrice(String fromPrice) {
		this.fromPrice = fromPrice;
	}

	public void setToPrice(String toPrice) {
		this.toPrice = toPrice;
	}

	public void setYearFrom(String yearFrom) {
		this.yearFrom = yearFrom;
	}

	public void setYearTo(String yearTo) {
		this.yearTo = yearTo;
	}

	public void setHpFrom(String hpFrom) {
		this.hpFrom = hpFrom;
	}

	public void setHpTo(String hpTo) {
		this.hpTo = hpTo;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public void setGearboxType(String gearboxType) {
		this.gearboxType = gearboxType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setExtras(List<String> extras) {
		this.extras = extras;
	}
}

	