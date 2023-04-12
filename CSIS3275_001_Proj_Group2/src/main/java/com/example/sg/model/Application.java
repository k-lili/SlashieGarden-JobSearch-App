package com.example.sg.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "application")
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	
	@Column (name="skills")
	public String skills;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "job_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonProperty("job")
	private Job job;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "application_status_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonProperty("application_status")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_type_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonProperty("userType")
	private UserType userType;
	
	@Column(name = "createdDateTime")
	private String createdDateTime;
	
	public Application() {
	}
	
//	public Application(Job job, Status status) {
//		this.job = job;
//		this.status = status;
//		
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyy HH:mm:ss");
//		this.createdDateTime = LocalDateTime.now().format(dateTimeFormatter);
//	}
	
	public Application(Job job, Status status, UserType userType, String skills) {
		this.job = job;
		this.status = status;
		this.userType = userType;
		this.skills = skills;

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
		this.createdDateTime = LocalDateTime.now().format(dateTimeFormatter);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createDateTime) {
		this.createdDateTime = createDateTime;
	}

}
