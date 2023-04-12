package com.example.sg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.example.sg.model.Application;
import com.example.sg.model.Status;
import com.example.sg.model.ApplicationRepository;
import com.example.sg.model.Job;
import com.example.sg.model.JobRepository;
import com.example.sg.model.StatusRepository;
import com.example.sg.model.UserType;
import com.example.sg.model.UserTypeRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ApplicationController {
	@Autowired
	ApplicationRepository applicationRepo;
	
	@Autowired
	StatusRepository statusRepo;
	
	@Autowired
	JobRepository jobRepo;
	
	@Autowired
	UserTypeRepository userTypeRepo;
	
	@GetMapping("/applications")
	public ResponseEntity<List<Application>> getAllApplication() {
		try {
			List<Application> applications = new ArrayList<>();
			applicationRepo.findAll().forEach(applications::add);

			if (applications.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(applications, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/applications/{id}")
	public ResponseEntity<Application> getApplication(@PathVariable Long id) {
		try {
			Optional<Application> application = applicationRepo.findById(id);

			if (application.isPresent()) {
				return new ResponseEntity<>(application.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/applications")
	public ResponseEntity<Application> createApplication(@RequestBody Map<String, Object> requestBody) {
		try {
			Optional<Job> jobData = jobRepo.findById(requestBody.get("jobId").toString());
			Optional<Status> statusData = statusRepo.findById(requestBody.get("statusId").toString());
			Optional<UserType> userTypeData = userTypeRepo.findByAppUserUserIdAndTypeTypeId(requestBody.get("appUserId").toString(), "2");
			String skillsData = requestBody.get("skills").toString();
			
			Application newApplication = new Application(jobData.get(), statusData.get(), userTypeData.get(), skillsData);
			applicationRepo.save(newApplication);
			jobData.get().addApplication(newApplication);
			statusData.get().addApplication(newApplication);
			userTypeData.get().addApplication(newApplication);//wrong info
			return new ResponseEntity<>(newApplication, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/applications/{id}")
	public ResponseEntity<Application> updateApplicationStatus(@PathVariable Long id, @RequestBody Map<String, Object> requestBody)

	{
		try {
			Optional<Application> applicationData = applicationRepo.findById(id);
			String statusId = requestBody.get("statusId").toString();
			Optional<Status> statusData = statusRepo.findById(statusId);

			if (applicationData.isPresent() && statusData.isPresent()) {
				
				Application updatedApplication = applicationData.get();
				updatedApplication.getStatus().removeApplication(updatedApplication);
				
				updatedApplication.setStatus(statusData.get());
				statusData.get().addApplication(updatedApplication);

				applicationRepo.save(updatedApplication);
				return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
