package com.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.tomcat.jni.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.model.Brand;
import com.model.CategoryEnum;
import com.model.EngineTypeEnum;
import com.model.ExtratypeEnum;
import com.model.GearboxEnum;
import com.model.Image;
import com.model.Model;
import com.model.Region;
import com.model.User;
import com.model.Vehicle;
import com.model.VehicleDto;
import com.model.VehicleTypeEnum;

@Service
@Transactional
public class VehicleService {
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Vehicle> getTopFourVehicles(){
		
		return em.createQuery("Select v from Vehicle v").setMaxResults(4).getResultList();
	}
	
	
	public List<String> getAllExtras(){
		List<String> extras = new ArrayList<>();
		
		for(ExtratypeEnum ex : ExtratypeEnum.values()){
			
				extras.add(ex.toString());
		}
		return extras;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vehicle> getAllVehicles(){
		List<Vehicle> vehicles = (List<Vehicle>) em.createQuery("Select v from Vehicle v").getResultList();
		return vehicles;
	}
	
	public Vehicle getVehicle(Long vehicleId){
		Vehicle vehicle = (Vehicle) em.createQuery("Select v from Vehicle v where v.id = :vehicleId")
				.setParameter("vehicleId", vehicleId).getSingleResult();
		return vehicle;
	}
	
	public User getUserByVehicleId(Long vehicleId){
		BigDecimal userId = (BigDecimal)em.createNativeQuery("Select user_id from vehicle where id = :vehicleId")
				.setParameter("vehicleId", vehicleId).getSingleResult();
		User u = (User)em.createQuery("Select u from User u where u.id = :userId")
				.setParameter("userId", Long.parseLong(userId.toString())).getSingleResult();
		return u;
		
	}
	
	public List<ExtratypeEnum> addExtra(List<String> extras){
		List<ExtratypeEnum> filtred = new ArrayList<>();
		
		for(String s : extras){
			for(ExtratypeEnum ex : ExtratypeEnum.values()){
				if(ex.toString().equals(s)){
					filtred.add(ex);
				}
			}
		}
		return filtred;
	}
	
	public Long addVehicle(Vehicle vehicle) {
		
		em.persist(vehicle);
		return vehicle.getId();
	}

	@SuppressWarnings("unchecked")
	public List<String> getBrands(){
		List<Brand> brands = (List<Brand>) em.createQuery("Select b from Brand b").getResultList();
		List<String> names = new ArrayList<>();
		for(Brand b : brands){
			names.add(b.getBrand());
		}
		return names;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getTypes(){
		List<String> types = new ArrayList<>();
		for(VehicleTypeEnum type : VehicleTypeEnum.values()){
			types.add(type.toString());
		}
		
		return types;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getCategories(){
		List<String> types = new ArrayList<>();
		for(CategoryEnum type : CategoryEnum.values()){
			types.add(type.toString());
		}
		
		return types;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getGearboxTypes(){
		List<String> types = new ArrayList<>();
		for(GearboxEnum type : GearboxEnum.values()){
			types.add(type.toString());
		}
		return types;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getEngineType(){
		List<String> types = new ArrayList<>();
		for(EngineTypeEnum type : EngineTypeEnum.values()){
			types.add(type.toString());
		}
		return types;
	}
	
	public List<String> getModelForBrand(String brandName){
		
		Brand brand = (Brand) em.createQuery("Select b from Brand b where b.brand = :brandName")
				.setParameter("brandName", brandName).getSingleResult();
		List<String> models = new ArrayList<>();
		
		if(brand.getModels().size() == 0){
			return models;
		}
		
		for(Model m : brand.getModels()){
			models.add(m.getModel());
		}
		return models;		
	}
	
		
	@SuppressWarnings("unchecked")
	public List<Vehicle> getVehiclesFilter2(String type, String categoryType, String brandName, String modelName, String engineType, 
			String gearboxType, List<String> selectedExtras){
		List<Vehicle> filtred = new ArrayList<Vehicle>();
		boolean haveExtras = true;
		
		List<Vehicle> allVehicles = (List<Vehicle>) em.createQuery("Select v from Vehicle v where v.type = :type and "
				+ "v.categoryType = :categoryType and v.brand = :brand and v.model = :model and "
				+ "v.engineType = :engineType and v.gearboxType = :gearboxType")
				.setParameter("type", VehicleTypeEnum.valueOf(type)).setParameter("categoryType", CategoryEnum.valueOf(categoryType))
				.setParameter("brand", brandName).setParameter("model", modelName)
				.setParameter("engineType", EngineTypeEnum.valueOf(engineType))
				.setParameter("gearboxType", GearboxEnum.valueOf(gearboxType)).getResultList();
		
		for(Vehicle v : allVehicles){
			for(String e : selectedExtras){
				if(!(v.getExtras().contains(ExtratypeEnum.valueOf(e)))){
					haveExtras = false;
					break;
				}
			}
			if(haveExtras){
				filtred.add(v);
			}
			haveExtras = true;
		}
		
		return filtred;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vehicle> getVehiclesFilter(VehicleDto dto){
		
		List<Vehicle> filtred = new ArrayList<Vehicle>();
		List<Vehicle> allVehicles = (List<Vehicle>) em.createQuery("Select v from Vehicle v").getResultList();
		//brand
		if(!(dto.getBrand().equals(""))){
			for(Vehicle v : allVehicles){
				if(!(v.getBrand().equals(dto.getBrand()))){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//region
		if(!(dto.getRegion().equals(""))){
			for(Vehicle v : allVehicles){
				if(!(v.getRegion().equals(dto.getRegion()))){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//price From
		if(!(dto.getFromPrice().equals(""))){
			BigDecimal priceFrom = new BigDecimal(Integer.parseInt(dto.getFromPrice()));
			
			for(Vehicle v : allVehicles){
				if(v.getPrice().compareTo(priceFrom) <= 0){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//price To
		if(!(dto.getToPrice().equals(""))){
			BigDecimal priceTo = new BigDecimal(Integer.parseInt(dto.getToPrice()));
			
			for(Vehicle v : allVehicles){
				if(v.getPrice().compareTo(priceTo) >= 0){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//year From
		if(!(dto.getYearFrom().equals(""))){
			int yearFrom = Integer.parseInt(dto.getYearFrom());
			for(Vehicle v : allVehicles){
				if(v.getYear() < yearFrom){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//year To
		if(!(dto.getYearTo().equals(""))){
			int yearTo = Integer.parseInt(dto.getYearTo());
			for(Vehicle v : allVehicles){
				if(v.getYear() > yearTo){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//hp From
		if(!(dto.getHpFrom().equals(""))){
			int hpFrom = Integer.parseInt(dto.getHpFrom());
			for(Vehicle v : allVehicles){
				if(v.getHorsePower() < hpFrom){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//hp To
		if(!(dto.getHpTo().equals(""))){
			int hpTo = Integer.parseInt(dto.getHpTo());
			for(Vehicle v : allVehicles){
				if(v.getHorsePower() > hpTo){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//model
		if(!(dto.getModel().equals(""))){
			for(Vehicle v : allVehicles){
				if(!(v.getModel().equals(dto.getModel()))){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//category
		if(!(dto.getCategoryType().equals(""))){
			for(Vehicle v : allVehicles){
				if(!(v.getCategoryType().equals(CategoryEnum.valueOf(dto.getCategoryType())))){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//type
		if(!(dto.getType().equals(""))){
			for(Vehicle v : allVehicles){
				if(!(v.getType().equals(VehicleTypeEnum.valueOf(dto.getType())))){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//engine
		if(!(dto.getEngineType().equals(""))){
			for(Vehicle v : allVehicles){
				if(!(v.getEngineType().equals(EngineTypeEnum.valueOf(dto.getEngineType())))){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//gearbox
		if(!(dto.getGearboxType().equals(""))){
			for(Vehicle v : allVehicles){
				if(!(v.getGearboxType().equals(GearboxEnum.valueOf(dto.getGearboxType())))){
					filtred.add(v);
				}
			}
			allVehicles.removeAll(filtred);
			filtred.removeAll(filtred);
		}
		//extras
		for(Vehicle v : allVehicles){
			for(String e : dto.getExtras()){
				if(!(v.getExtras().contains(ExtratypeEnum.valueOf(e)))){
					filtred.add(v);
					break;
				}
			}
		}
		allVehicles.removeAll(filtred);
		filtred.removeAll(filtred);
		
		return allVehicles;
	}
	
	public void saveUploadedFiles(List<MultipartFile> files, Long vehicleId) throws IOException{
		for(MultipartFile file : files){
			Vehicle vehicle = (Vehicle) em.createQuery("Select v from Vehicle v where v.id = :vehicleId")
					.setParameter("vehicleId", vehicleId).getSingleResult();
			Image image = new Image();
			image.setName(file.getOriginalFilename());
			image.setPicture(file.getBytes());
			image.setVehicle(vehicle);
			em.persist(image);
		}

	}
	@SuppressWarnings("unchecked")
	public List<Image> getImages(Long vehicleId){
		
		Vehicle vehicle = (Vehicle) em.createQuery("Select v from Vehicle v where v.id = :vehicleId")
				.setParameter("vehicleId", vehicleId).getSingleResult();
		
		return vehicle.getImages();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRegions(){
		List<Region> regions = (List<Region>)em.createQuery("Select r from Region r")
				.getResultList();
		List<String>regionNames = new ArrayList<String>();
		for(Region r: regions){
			regionNames.add(r.getRegion());
		}
		return regionNames;		
	}
	
	@SuppressWarnings("unchecked")
	public String bindUserToVehicle(Long vehicleId, Long userId){
		User u = (User)em.createQuery("Select u from User u where u.id = :userId")
				.setParameter("userId", userId).getSingleResult();
		Vehicle v = (Vehicle)em.createQuery("Select v from Vehicle v where v.id = :vehicleId")
				.setParameter("vehicleId", vehicleId).getSingleResult();
		
		v.setUser(u);
		em.persist(v);
		return "Success";
	}
	

}
