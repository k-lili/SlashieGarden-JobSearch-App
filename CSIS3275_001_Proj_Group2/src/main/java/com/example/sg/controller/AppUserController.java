package com.example.sg.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sg.model.AppUser;
import com.example.sg.model.AppUserRepository;
import com.example.sg.model.Job;
import com.example.sg.model.TypeRepository;
import com.example.sg.model.UserTypeRepository;

@CrossOrigin(origins="http://localhost:8081") //for vue.js
@RestController
@RequestMapping("/api")
public class AppUserController {

	@Autowired
	AppUserRepository appUserRepo;
	
	@Autowired
	UserTypeRepository userTypeRepo;
	
	@Autowired
	TypeRepository typeRepo;
	
	@GetMapping("/app_users")
	public ResponseEntity<List<AppUser>> getAllAppUsers(
			@RequestParam(required=false) String lastName){
		
		try {
			List<AppUser> appUsers = new ArrayList<AppUser>();
			
			if (lastName == null) {
				appUserRepo.findAll().forEach(appUsers::add);
			} else {
				appUserRepo.findByLastNameContainingIgnoreCase(lastName).forEach(appUsers::add);
			}
			
			if (appUsers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(appUsers, HttpStatus.OK);
				
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/app_users/{id}")
	public ResponseEntity<AppUser> getAppUser(@PathVariable Long id){
		
		try {
			Optional<AppUser> appUser = appUserRepo.findById(id);
			
			if (appUser.isPresent()) {
				return new ResponseEntity<>(appUser.get(),HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@PostMapping("/app_users")
//	public ResponseEntity<AppUser> createAppUser(@RequestBody Map<String, Object> requestBody){
//		try {
//			String user_firstName = requestBody.get("firstName").toString();
//			String user_lastName = requestBody.get("lastName").toString();
//			String user_gender = requestBody.get("gender").toString();
//			String user_email = requestBody.get("email").toString();
//			String user_mobileNum = requestBody.get("mobileNum").toString();
//			
//			if(appUserRepo.findByEmail(user_email).isPresent()) {
//				System.out.println("User exists already!");
//				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//			
//			AppUser newUser = new AppUser(user_firstName,user_lastName,user_gender,user_email,user_mobileNum);
//			appUserRepo.save(newUser);
//			System.out.println("User created and saved");
//			Optional<AppUser> appUserData = appUserRepo.findByEmail(user_email);
//			
//			String userChosenType = requestBody.get("typeId").toString();
//			System.out.println(userChosenType);
//			
//			ArrayList<String> userChosenTypeIds = new ArrayList<>();
//			for(int i = 0; i < userChosenType.length(); i++) {
//				if(userChosenType.charAt(i) == '1' || userChosenType.charAt(i) == '2' || userChosenType.charAt(i) == '3') {
//					userChosenTypeIds.add(String.valueOf(userChosenType.charAt(i)));
//				}
//			}
//			
//			for(int i = 0; i < userChosenTypeIds.size(); i++) {
//				Optional<Type> typeData = typeRepo.findByTypeId(userChosenTypeIds.get(i));
//
//				UserType newUserType = new UserType(
//						appUserData.get(),
//						typeData.get());
//				userTypeRepo.save(newUserType);
//			}
//			
//			return new ResponseEntity<>(HttpStatus.CREATED);				
//		}catch(Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	//for setting Valid/Invalid
	@PutMapping("/app_users/{id}")
	public ResponseEntity<AppUser> updateAppUser (@PathVariable Long id, @RequestBody AppUser appUser){
		
		try {
			Optional<AppUser> appUserItem = appUserRepo.findById(id);
			
			if (appUserItem.isPresent()) {
				AppUser _appUser = appUserItem.get();
				_appUser.setFirstName(appUser.getFirstName());
				_appUser.setLastName(appUser.getLastName());
				_appUser.setGender(appUser.getGender());
				_appUser.setEmail(appUser.getEmail());
				_appUser.setMobileNum(appUser.getMobileNum());
				_appUser.setValid(appUser.isValid());
				
				appUserRepo.save(_appUser);
				
				return new ResponseEntity<>(_appUser,HttpStatus.OK);	
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
}
