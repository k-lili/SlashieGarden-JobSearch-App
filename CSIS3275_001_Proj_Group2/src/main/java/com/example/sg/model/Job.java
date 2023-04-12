package com.example.sg.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "job")
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "job_date")
	private String jobDate;
	
	@Column(name = "start_time")
	private LocalTime startTime;
	
	@Column(name = "end_time")
	private LocalTime endTime;
	
	@Column(name = "duration")
	private float duration;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "hourly_wage")
	private double hrWage;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_type_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonProperty("user_type_id")
	private UserType userType;
	
	@Column(name = "create_date_time")
	private String createdDateTime;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "job_status_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonProperty("job_status")
	private Status status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "job", fetch = FetchType.LAZY) 
	@JsonIgnore
	private Set<Application> applications = new HashSet<>();
	
	public Job() {
		
	}
	
	public Job(String jobTitle, String description, String jobDate, LocalTime startTime,
			LocalTime endTime, String location, double hrWage, UserType userType) {
		this.jobTitle = jobTitle;
		this.description = description;
		this.jobDate = jobDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = (float) Duration.between(startTime, endTime).getSeconds() / 3600;
		this.location = location;
		this.hrWage = hrWage;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
		this.createdDateTime = LocalDateTime.now().format(dateTimeFormatter);
		
		this.status = Status.OPEN;
		this.userType = userType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getHrWage() {
		return hrWage;
	}

	public void setHrWage(double hrWage) {
		this.hrWage = hrWage;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType (UserType userType) {
		this.userType = userType;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}
	
	public void addApplication(Application application) {
		this.applications.add(application);
		application.setJob(this);
	}
}
