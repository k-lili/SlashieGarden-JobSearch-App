package com.example.sg.controller;

import java.time.LocalTime;
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

import com.example.sg.model.UserTypeRepository;
import com.example.sg.model.Application;
import com.example.sg.model.ApplicationRepository;
import com.example.sg.model.Job;
import com.example.sg.model.JobRepository;
import com.example.sg.model.Status;
import com.example.sg.model.StatusRepository;

@CrossOrigin(origins = "http://localhost:8081") // used for vue.js later
@RestController
@RequestMapping("/api")
public class JobController {

	@Autowired
	JobRepository jobRepo;
	
	@Autowired
	StatusRepository statusRepo;
	
	@Autowired
	UserTypeRepository userTypeRepo;
	
	@Autowired
	ApplicationRepository applicationRepo;
	
	@GetMapping("/jobs")
	public ResponseEntity<List<Job>> getAllJob(
			@RequestParam(required=false) String jobTitle) {
		try {
			List<Job> jobs = new ArrayList<>();
			
			if (jobTitle == null) {
				System.out.println("jobTitle null");
				jobRepo.findAll().forEach(jobs::add);
			} else {
				jobRepo.findByJobTitleContainingIgnoreCase(jobTitle).forEach(jobs::add);
				System.out.println("JobTitle search triggered!");
			}

			if (jobs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(jobs, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/jobs/{id}")
	public ResponseEntity<Job> getJob(@PathVariable Long id) {
		try {
			Optional<Job> job = jobRepo.findById(id);
			if(job.isPresent()) {
				return new ResponseEntity<>(
						job.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(
					HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			return new ResponseEntity<>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//To get list of applications of the job
    @GetMapping("/jobs/{id}/applications")
    public ResponseEntity<List<Application>> getJobApplications(@PathVariable("id") long id) {
        Optional<Job> jobData = jobRepo.findById(id);
        List<Application> applications = new ArrayList<>();
        if (jobData.isPresent()) {
            applicationRepo.findByJob(jobData.get()).forEach(applications::add);
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping("/jobs")
	public ResponseEntity<Job> createJob(@RequestBody Map<String, Object> requestBody){
		try {
			int startTimeHour = Integer.parseInt(requestBody.get("startTime").toString().substring(0,2));
			int startTimeMinute = Integer.parseInt(requestBody.get("startTime").toString().substring(3,5));
			int endTimeHour = Integer.parseInt(requestBody.get("endTime").toString().substring(0,2));
			int endTimeMinute = Integer.parseInt(requestBody.get("endTime").toString().substring(3,5));
			double hrWage = Double.parseDouble(requestBody.get("hourly_wage").toString());

			Job newJob = new Job(requestBody.get("jobTitle").toString(), requestBody.get("description").toString(), requestBody.get("jobDate").toString(),
					LocalTime.of(startTimeHour, startTimeMinute), LocalTime.of(endTimeHour, endTimeMinute),requestBody.get("location").toString(), 
					hrWage, userTypeRepo.findByAppUserUserIdAndTypeTypeId(requestBody.get("appUserId").toString(), "1").get());
			jobRepo.save(newJob);
			return new ResponseEntity<>(
					newJob, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/jobs/{id}")
	public ResponseEntity<Job> updateJobStatus(@PathVariable("id") long id, @RequestBody Map<String, Object> requestBody) {
		try {
			Optional<Job> jobData = jobRepo.findById(id);
			String statusId = requestBody.get("statusId").toString();
			Optional<Status> statusData = statusRepo.findById(statusId);

			if (jobData.isPresent() && statusData.isPresent()) {
				
				Job updatedJob = jobData.get();
				updatedJob.getStatus().removeJob(updatedJob);
				updatedJob.setStatus(statusData.get());
				statusData.get().addJob(updatedJob);

				jobRepo.save(updatedJob);
				return new ResponseEntity<>(updatedJob, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
