package com.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;
import com.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	public UserService us;
	
	@RequestMapping(value="/register", method = RequestMethod.POST, 
			consumes = "application/json")
	@ResponseBody
	public ResponseEntity<?> register(@RequestBody User user) throws IOException{
		
			return new ResponseEntity<String>(us.register(user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST, 
			consumes = "application/json")
	@ResponseBody
	public String login(@RequestBody User user) throws IOException{
		
			return us.login(user);
	}

}
