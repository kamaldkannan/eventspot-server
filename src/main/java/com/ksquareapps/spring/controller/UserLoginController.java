package com.ksquareapps.spring.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksquareapps.spring.model.UserLogin;

/**
 * Handles requests for the UserLogin service.
 */
@Controller
public class UserLoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	
	//Map to store employees, ideally we should use database
	Map<String, UserLogin> userData = new HashMap<String, UserLogin>();
	
	//Test
	@RequestMapping(value = RestURIConstants.DUMMY_USER, method = RequestMethod.GET)
	public @ResponseBody UserLogin getDummyEmployee() {
		logger.info("Start getDummyUser");
		UserLogin user = new UserLogin();
		user.setFirstName("Dummy");
		user.setLastName("Jimmy");
		user.setEmail("dummy@dummy.com");
		user.setPassword("dummy");
		user.setCreatedDate(new Date());
		userData.put(user.getEmail(), user);
		return user;
	}
	
	//Test
	@RequestMapping(value = RestURIConstants.GET_USER, method = RequestMethod.GET)
	public @ResponseBody UserLogin getUser(@PathVariable("email") String email) {
		
		email = email + ".com";
		logger.info("Start getUser. Email="+email);
		
		return userData.get(email);
	}
	
	@RequestMapping(value = RestURIConstants.GET_ALL_USER, method = RequestMethod.GET)
	public @ResponseBody List<UserLogin> getAllUsers() {
		logger.info("Start getAllUsers.");
		List<UserLogin> emps = new ArrayList<UserLogin>();
		Set<String> userEmailKeys = userData.keySet();
		for(String i : userEmailKeys){
			emps.add(userData.get(i));
		}
		return emps;
	}
	
	
	@RequestMapping(value = RestURIConstants.LOGIN_USER, method = RequestMethod.POST)
	public @ResponseBody boolean loginUser(@RequestBody UserLogin user) {
		logger.info("Start loginUser.");
		
		UserLogin storedUser = userData.get(user.getEmail());
		if((storedUser!=null)&&(storedUser.getPassword()!=null)&&(storedUser.getPassword().equals(user.getPassword()))){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	@RequestMapping(value = RestURIConstants.SIGNUP_USER , method = RequestMethod.POST)
	public @ResponseBody UserLogin signupUser(@RequestBody UserLogin user) {
		logger.info("Start signupUser.");
		user.setCreatedDate(new Date());
		userData.put(user.getEmail(), user);
		return user;
	}
	
	@RequestMapping(value = RestURIConstants.DELETE_USER, method = RequestMethod.PUT)
	public @ResponseBody UserLogin deleteUser(@PathVariable("email") String email) {
		email = email + ".com";
		logger.info("Start deleteUser.");
		UserLogin user = userData.get(email);
		userData.remove(email);
		return user;
	}
	
}
