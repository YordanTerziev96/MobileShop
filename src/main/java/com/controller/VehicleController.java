package com.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.model.ExtratypeEnum;
import com.model.Image;
import com.model.Model;
import com.model.User;
import com.model.Vehicle;
import com.model.VehicleDto;
import com.service.VehicleService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class VehicleController {
	
	@Autowired
	public VehicleService vs;
	
	@RequestMapping(value = "/models", method = RequestMethod.GET, params = {"brandName"})
	@ResponseBody
	public ResponseEntity<?> getModels(String brandName){
		
		List<String> models = vs.getModelForBrand(brandName);
		return new ResponseEntity<>(models, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getVehicle", method = RequestMethod.GET, params = {"vehicleId"})
	@ResponseBody
	public ResponseEntity<?> getVehicle(Long vehicleId){
		
		return new ResponseEntity<>(vs.getVehicle(vehicleId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/types", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getTypes(){
			
			return new ResponseEntity<List<String>>(vs.getTypes(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getRegions(){
		
		return new ResponseEntity<List<String>>(vs.getRegions(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/gearboxes", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGearboxes(){
		
		return new ResponseEntity<List<String>>(vs.getGearboxTypes(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/engines", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getEngineTypes(){
		
		return new ResponseEntity<List<String>>(vs.getEngineType(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/allExtras", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllExtras(){
		
		return new ResponseEntity<List<String>>(vs.getAllExtras(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/brands", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getBrands(){
		
		return new ResponseEntity<List<String>>(vs.getBrands(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getCategories(){
		
		return new ResponseEntity<List<String>>(vs.getCategories(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allVehicles", method = RequestMethod.GET)
	@ResponseBody
	public List<Vehicle> getAllVehicles(){
		
		return vs.getAllVehicles();
	}
	
	@RequestMapping(value="/addVehicle", method = RequestMethod.POST, 
			consumes = "application/json")
	@ResponseBody
	public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle) throws IOException{
		
			return new ResponseEntity<Long>(vs.addVehicle(vehicle), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/filterVehicles", method = RequestMethod.POST, 
			consumes = "application/json")
	@ResponseBody
	public List<Vehicle> getFiltredVehicles(@RequestBody VehicleDto dto) throws IOException{
		
		return vs.getVehiclesFilter(dto);
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/rest/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile, 
			@RequestParam("vehicleID") Long vehicleId) {


		if (uploadfile.isEmpty()) {
			return new ResponseEntity("You must select a file!", HttpStatus.OK);
		}

		try {

			vs.saveUploadedFiles(Arrays.asList(uploadfile), vehicleId);

		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), 
				HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/getImages", method = RequestMethod.GET, params = {"vehicleId"})
	@ResponseBody
	public ResponseEntity<?> getImages(Long vehicleId) throws UnsupportedEncodingException{

		List<Image> images = vs.getImages(vehicleId);	
		List<String> encoded = new ArrayList<>();
		for(Image i : images){			
			byte[] encodeBase64 = Base64.encodeBase64(i.getPicture());
			String base64Encoded = new String(encodeBase64, "UTF-8");
			encoded.add(base64Encoded);
			
		}
		  
		return new ResponseEntity<>(encoded, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserByVehicleId", method = RequestMethod.GET, params = {"vehicleId"})
	@ResponseBody
	public ResponseEntity<?> getUserByVehicleId(Long vehicleId){

		return new ResponseEntity<>(vs.getUserByVehicleId(vehicleId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bindUser", method = RequestMethod.GET, params = {"vehicleId", "userId"})
	@ResponseBody
	public ResponseEntity<?> bindUserToVehicle(Long vehicleId, Long userId){

		return new ResponseEntity<>(vs.bindUserToVehicle(vehicleId, userId), HttpStatus.OK);
	}

}
