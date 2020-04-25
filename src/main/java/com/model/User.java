package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
	
	@Column(name = "username", nullable = false, unique=true)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "enabled", nullable = false)
	private int enabled;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "mobilePhone", nullable = false)
	private String mobilePhone;
	
	public String getEmail() {
		return email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getUsername() {
		return username;
	}

	public int getEnabled() {
		return enabled;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	@OneToMany(mappedBy = "user")
	private List<Vehicle> vehicles = new ArrayList<>();

}
