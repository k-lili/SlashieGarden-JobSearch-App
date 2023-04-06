package com.example.sg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sg.model.AppUser;
import com.example.sg.model.AppUserRepository;
import com.example.sg.model.Type;
import com.example.sg.model.TypeRepository;
import com.example.sg.model.UserType;
import com.example.sg.model.UserTypeRepository;

@CrossOrigin(origins="http://localhost:8081") //for Vue.js
@RestController
@RequestMapping("/api")
public class UserTypeController {

	@Autowired
	UserTypeRepository userTypeRepo;
	
	@Autowired
	AppUserRepository appUserRepo;
	
	@Autowired
	TypeRepository typeRepo;
	
	@GetMapping("/user_types")
	public ResponseEntity<List<UserType>> getAllUserTypes(){
		try {
			
			List<UserType> userTypes = new ArrayList<UserType>();
			userTypeRepo.findAll().forEach(userTypes::add);
			
			if(userTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(userTypes, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/user_types/{id}")
	public ResponseEntity<UserType> getUserType(@PathVariable Long id){
		
		try {
			
			Optional<UserType> userType = userTypeRepo.findById(id);
			
			if (userType.isPresent()) {
				return new ResponseEntity<>(userType.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}		
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/user_types")
	public ResponseEntity<List<UserType>> createUserType(@RequestBody Map<String, Object> requestBody){
		try {
			String user_firstName = requestBody.get("firstName").toString();
			String user_lastName = requestBody.get("lastName").toString();
			String user_gender = requestBody.get("gender").toString();
			String user_email = requestBody.get("email").toString();
			String user_mobileNum = requestBody.get("mobileNum").toString();
			
			if(appUserRepo.findByEmail(user_email).isPresent()) {
				System.out.println("User exists already!");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			AppUser newUser = new AppUser(user_firstName,user_lastName,user_gender,user_email,user_mobileNum);
			appUserRepo.save(newUser);
			System.out.println("User created and saved");
			Optional<AppUser> appUserData = appUserRepo.findByEmail(user_email);
			
			String userChosenType = requestBody.get("typeId").toString();
			System.out.println(userChosenType);
			
			ArrayList<String> userChosenTypeIds = new ArrayList<>();
			for(int i = 0; i < userChosenType.length(); i++) {
				if(userChosenType.charAt(i) == '1' || userChosenType.charAt(i) == '2' || userChosenType.charAt(i) == '3') {
					userChosenTypeIds.add(String.valueOf(userChosenType.charAt(i)));
				}
			}
			
			List<UserType> newUserTypes = new ArrayList<UserType>();
			
			for(int i = 0; i < userChosenTypeIds.size(); i++) {
				Optional<Type> typeData = typeRepo.findByTypeId(userChosenTypeIds.get(i));

				UserType newUserType = new UserType(
						appUserData.get(),
						typeData.get());
				userTypeRepo.save(newUserType);
				newUserTypes.add(newUserType);
				
			}
			
//			String appUserId = requestBody.get("userId").toString();
//			System.out.println(appUserId);
//			Optional<AppUser> appUserData = appUserRepo.findById(Long.parseLong(appUserId));
//			
//			String appUserType = requestBody.get("typeId").toString();
//			System.out.println(appUserType);
//			Optional<Type> typeData = typeRepo.findById(Long.parseLong(appUserType));
//
//			UserType newUserType = new UserType(
//					appUserData.get(),
//					typeData.get());
//			System.out.println("UserType created");
//			userTypeRepo.save(newUserType);
	
			return new ResponseEntity<>(newUserTypes, HttpStatus.CREATED);				
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}